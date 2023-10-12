/*
 * Copyright 2023 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayasuryat.mendable

import com.jayasuryat.mendable.MendableReportGeneratorRequest.ExportType
import com.jayasuryat.mendable.MendableReportGeneratorRequest.IncludeModules
import com.jayasuryat.mendable.export.html.MendableHtmlPage
import com.jayasuryat.mendable.export.json.jsonExportContent
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ComposableSignaturesReportFile
import com.jayasuryat.mendable.model.ComposeCompilerMetricsExportModel
import com.jayasuryat.mendable.model.ComposeCompilerMetricsExportModel.ModuleDetails
import com.jayasuryat.mendable.model.ComposeCompilerMetricsExportModel.Overview
import com.jayasuryat.mendable.parser.getComposableSignaturesReportFileParser
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport.ComposableDetails
import com.jayasuryat.mendable.scanner.ModuleFactory
import com.jayasuryat.mendable.scanner.scanForComposableSignaturesReportFiles
import dev.drewhamilton.poko.Poko
import kotlinx.coroutines.*
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.math.roundToInt

/**
 * This class is responsible for generating a mendable report based on the given request.
 *
 * @property computationDispatcher The dispatcher used for computational tasks.
 * @property ioDispatcher The dispatcher used for I/O tasks.
 */
public class MendableReportGenerator(
    private val computationDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    public constructor() : this(
        computationDispatcher = Dispatchers.Default,
        ioDispatcher = Dispatchers.IO,
    )

    /**
     * Generates a mendable report based on the given [request] and reports progress to the [progress] handler.
     *
     * @param request The request containing the necessary parameters for report generation.
     * @param progress The progress handler that receives updates on the report generation progress.
     * @return The result of the report generation.
     */
    public suspend fun generate(
        request: MendableReportGeneratorRequest,
        progress: (progress: Progress) -> Unit = {},
    ): Progress.Result {

        return try {
            unsafeGenerator(
                request = request,
                progress = progress
            )
        } catch (ex: Exception) {
            if (ex is CancellationException) throw ex
            val error = Progress.Error(throwable = ex)
            progress(error)
            error
        }
    }

    private suspend fun unsafeGenerator(
        request: MendableReportGeneratorRequest,
        progress: (progress: Progress) -> Unit,
    ): Progress.Result {

        request.validate()

        progress(Progress.Initiated)

        val moduleFactory: ModuleFactory = request.moduleProducer.toModuleFactory()
        val metricsFiles: List<ComposableSignaturesReportFile> = withContext(ioDispatcher) {
            request.scanPaths.map { scanPath ->
                async {
                    scanForComposableSignaturesReportFiles(
                        directory = File(scanPath),
                        scanRecursively = request.scanRecursively,
                        moduleFactory = moduleFactory,
                    )
                }
            }.awaitAll().flatten()
        }

        if (metricsFiles.isEmpty()) {
            val result = Progress.NoMetricsFilesFound
            progress(result)
            return result
        }

        progress(Progress.MetricsFilesFound(files = metricsFiles))

        val parsed: ParseResult = withContext(computationDispatcher) {
            metricsFiles.parseMetrics()
        }

        progress(
            Progress.MetricsFilesParsed(
                parsedSuccessfully = parsed.successful.size,
                errors = parsed.errors
            )
        )

        val meta = withContext(computationDispatcher) {
            parsed.successful
                .toExportModel(includeModules = request.includeModules)
                .getExportContent(type = request.exportType)
        }

        val saveDir = withContext(ioDispatcher) {
            meta.save(
                outputName = request.outputFileName,
                outputDirectory = request.outputPath,
            )
        }

        val successResult = Progress.SuccessfullyCompleted(
            outputPath = saveDir,
            exportType = request.exportType,
        )

        progress(successResult)
        return successResult
    }

    private fun MendableReportGeneratorRequest.validate() {

        scanPaths.forEach { scanPath ->
            require(scanPath.isNotEmpty()) { "scanPath cannot be empty" }
            val input = File(scanPath)
            require(input.exists()) { "$scanPath does not exist" }
            require(input.isDirectory) { "$scanPath is not a directory" }
        }

        require(outputPath.isNotEmpty()) { "outputPath cannot be empty" }

        require(outputFileName.isNotEmpty()) { "outputFileName cannot be empty" }
    }

    private fun List<ComposableSignaturesReportFile>.parseMetrics(): ParseResult {
        val parser = getComposableSignaturesReportFileParser()

        val parsed = mutableListOf<ComposableSignaturesReport>()
        val errors = mutableListOf<Pair<String, Throwable>>()

        this.forEach { file ->
            runCatching { parser.parse(file) }
                .onSuccess { value: ComposableSignaturesReport -> parsed += value }
                .onFailure { throwable: Throwable -> errors += (file.file.path to throwable) }
        }

        return ParseResult(
            successful = parsed,
            errors = errors.map { (fileName, throwable) ->
                Progress.MetricsFilesParsed.ParseError(
                    fileName = fileName,
                    throwable = throwable,
                )
            }
        )
    }

    private fun List<ComposableSignaturesReport>.toExportModel(
        includeModules: IncludeModules,
    ): ComposeCompilerMetricsExportModel {

        fun List<ComposableSignaturesReport>.filterWithWarnings(): List<ComposableSignaturesReport> {
            return this.filter { report ->
                report.composables.any { composable -> composable.isRestartable && !composable.isSkippable }
            }
        }

        fun List<ComposableSignaturesReport>.toOverview(): Overview {

            val allComposables: List<ComposableDetails> = this.flatMap { report -> report.composables }
            val restartable = allComposables.filter { composable -> composable.isRestartable }
            val skippable = restartable.filter { composable -> composable.isSkippable }

            val percentage: Int = if (restartable.isEmpty()) 100
            else ((skippable.count() * 100f) / restartable.count()).roundToInt()

            return Overview(
                totalComposables = allComposables.size,
                restartableComposables = restartable.size,
                skippableComposables = skippable.size,
                skippablePercentage = percentage,
            )
        }

        fun ComposableSignaturesReport.toModuleDetails(): ModuleDetails {
            return ModuleDetails(
                overview = listOf(this).toOverview(),
                report = this,
            )
        }

        val parsed: List<ComposableSignaturesReport> = this
        val reporting: List<ComposableSignaturesReport> = when (includeModules) {
            IncludeModules.ALL -> parsed
            IncludeModules.WITH_WARNINGS -> parsed.filterWithWarnings()
        }
        val mapped = reporting.map { report -> report.toModuleDetails() }

        return ComposeCompilerMetricsExportModel(
            modules = mapped,
            overview = parsed.toOverview(),
            totalModulesScanned = parsed.size,
            totalModulesReported = reporting.size,
        )
    }

    private fun ComposeCompilerMetricsExportModel.getExportContent(
        type: ExportType,
    ): ExportMeta {

        val content: String = when (type) {
            ExportType.HTML -> MendableHtmlPage(report = this)
            ExportType.JSON -> this.jsonExportContent()
        }

        return ExportMeta(
            content = content,
            extension = when (type) {
                ExportType.HTML -> "html"
                ExportType.JSON -> "json"
            }
        )
    }

    private data class ExportMeta(
        val content: String,
        val extension: String,
    )

    private data class ParseResult(
        val successful: List<ComposableSignaturesReport>,
        val errors: List<Progress.MetricsFilesParsed.ParseError>,
    )

    private fun ExportMeta.save(
        outputName: String,
        outputDirectory: String,
    ): String {

        val directory = File(Paths.get(outputDirectory).absolutePathString())
        directory.mkdirs()
        val file = File("${directory.absolutePath}/$outputName.$extension")
        file.writeText(content)

        return file.absolutePath
    }

    /** Represents the progress of Mendable report generation.*/
    public sealed interface Progress {

        /** Represents a terminal state of the report generation process.*/
        public sealed interface Result

        /** Represents the initial state of the report generation process.*/
        public object Initiated : Progress {
            override fun toString(): String = "Progress.Initiated"
        }

        /**
         * Represents the state when metrics files are found for processing.
         *
         * @property files The list of metrics files found.
         */
        @Poko
        public class MetricsFilesFound(
            public val files: List<ComposableSignaturesReportFile>,
        ) : Progress

        /**
         * Represents the state when metrics files are parsed, indicating count of successful and failed parsing.
         *
         * @property parsedSuccessfully The count of metrics files parsed successfully.
         * @property errors The list of parse errors.
         */
        @Poko
        public class MetricsFilesParsed(
            public val parsedSuccessfully: Int,
            public val errors: List<ParseError>,
        ) : Progress {

            /** The count of metrics files which the parser was unable to parse. **/
            public val failedToParse: Int = errors.size

            /**
             * Represents an error that occurred during metrics file parsing.
             *
             * @property fileName The name of the file that caused the error.
             * @property throwable The throwable that occurred during parsing.
             */
            @Poko
            public class ParseError(
                public val fileName: String,
                public val throwable: Throwable,
            ) : RuntimeException(throwable)
        }

        /**
         * Represents the terminal state when the report generation process has completed successfully.
         *
         * @property outputPath The output path where the result is stored.
         * @property exportType The type of export used for the result.
         */
        @Poko
        public class SuccessfullyCompleted(
            public val outputPath: String,
            public val exportType: ExportType,
        ) : Progress, Result

        /** Represents the terminal state where when no metrics files are found and processing has stopped. */
        public object NoMetricsFilesFound : Progress, Result {
            override fun toString(): String = "Progress.NoMetricsFilesFound"
        }

        /**
         * Represents the terminal state where an error has occurred and further processing has been stopped in the
         * report generation process.
         *
         * @property throwable The throwable associated with the error.
         */
        @Poko
        public class Error(
            public val throwable: Throwable,
        ) : Progress, Result
    }
}
