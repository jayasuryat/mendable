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

import com.google.gson.Gson
import com.jayasuryat.mendable.MendableReportGenerator.Progress
import com.jayasuryat.mendable.MendableReportGeneratorRequest.ExportType
import com.jayasuryat.mendable.MendableReportGeneratorRequest.IncludeModules
import com.jayasuryat.mendable.model.ComposeCompilerMetricsExportModel
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

internal class MendableReportGeneratorTest {

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    private val generator = MendableReportGenerator()

    @Test
    fun `should throw error for empty scan path`() = runTest {

        val request = MendableReportGeneratorRequest(
            scanPath = "",
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "output",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.Error>()
    }

    @Test
    fun `should throw error for empty output path`() = runTest {

        val request = MendableReportGeneratorRequest(
            scanPath = temporaryFolder.root.path,
            outputPath = "",
            scanRecursively = false,
            outputFileName = "output",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.Error>()
    }

    @Test
    fun `should throw error for empty output file name`() = runTest {

        val request = MendableReportGeneratorRequest(
            scanPath = temporaryFolder.root.path,
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.Error>()
    }

    @Test
    fun `should throw error missing compose report files`() = runTest {

        val request = MendableReportGeneratorRequest(
            scanPath = temporaryFolder.root.path,
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "output",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.NoMetricsFilesFound>()
    }

    @Test
    fun `should result in success for valid html inputs`() = runTest {

        val path = this::class.java.classLoader?.getResource("app_release-composables.txt")?.path
        require(!path.isNullOrEmpty())

        val resourceRoot = File(path).parent

        val request = MendableReportGeneratorRequest(
            scanPath = resourceRoot,
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "report",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.SuccessfullyCompleted>()

        result.outputPath shouldBe "${temporaryFolder.root.path}${File.separator}report.html"
        result.exportType shouldBe ExportType.HTML

        val output = File(result.outputPath)
        output.shouldExist()
        output.shouldBeAFile()
    }

    @Test
    fun `should result in success for valid json inputs`() = runTest {

        val path = this::class.java.classLoader?.getResource("app_release-composables.txt")?.path
        require(!path.isNullOrEmpty())

        val resourceRoot = File(path).parent

        val request = MendableReportGeneratorRequest(
            scanPath = resourceRoot,
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "report",
            exportType = ExportType.JSON,
            includeModules = IncludeModules.ALL,
        )

        val result: Progress.Result = generator.generate(request = request)

        result.shouldBeInstanceOf<Progress.SuccessfullyCompleted>()

        result.outputPath shouldBe "${temporaryFolder.root.path}${File.separator}report.json"
        result.exportType shouldBe ExportType.JSON

        val output = File(result.outputPath)
        output.shouldExist()
        output.shouldBeAFile()
    }

    @Test
    fun `should consider multiple scan paths for report generation`() = runTest {

        val path1 = this::class.java.classLoader?.getResource("app_release-composables.txt")?.path
        val path2 = this::class.java.classLoader?.getResource("child/child_release-composables.txt")?.path
        require(!path1.isNullOrEmpty())
        require(!path2.isNullOrEmpty())

        val resourceRoot = File(path1).parent
        val childRoot = File(path2).parent

        val request = MendableReportGeneratorRequest(
            scanPaths = listOf(resourceRoot, childRoot),
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "report",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        var foundMetrics: Progress.MetricsFilesFound? = null
        generator.generate(request = request) { progress ->
            if (progress is Progress.MetricsFilesFound) {
                foundMetrics = progress
            }
        }

        val metrics = foundMetrics

        metrics.shouldNotBeNull()
        metrics.files.size shouldBe 2
    }

    @Test
    fun `should output all steps of generation`() = runTest {

        val path = this::class.java.classLoader?.getResource("app_release-composables.txt")?.path
        require(!path.isNullOrEmpty())

        val resourceRoot = File(path).parent

        val request = MendableReportGeneratorRequest(
            scanPath = resourceRoot,
            outputPath = temporaryFolder.root.path,
            scanRecursively = false,
            outputFileName = "report",
            exportType = ExportType.HTML,
            includeModules = IncludeModules.ALL,
        )

        val progresses: MutableList<Progress> = mutableListOf()
        generator.generate(request = request) { progress ->
            progresses.add(progress)
        }

        progresses.removeFirst().shouldBeInstanceOf<Progress.Initiated>()
        progresses.removeFirst().shouldBeInstanceOf<Progress.MetricsFilesFound>()
        progresses.removeFirst().shouldBeInstanceOf<Progress.MetricsFilesParsed>()
        progresses.removeFirst().shouldBeInstanceOf<Progress.SuccessfullyCompleted>()
    }

    @Test
    fun `should skip files without warnings steps of generation`() = runTest {

        val path = this::class.java.classLoader?.getResource("app_release-composables.txt")?.path
        require(!path.isNullOrEmpty())

        val resourceRoot = File(path).parent

        val request = MendableReportGeneratorRequest(
            scanPath = resourceRoot,
            outputPath = temporaryFolder.root.path,
            scanRecursively = true,
            outputFileName = "report",
            exportType = ExportType.JSON,
            includeModules = IncludeModules.WITH_WARNINGS,
        )

        val result: Progress.Result = generator.generate(request = request)
        result.shouldBeInstanceOf<Progress.SuccessfullyCompleted>()

        val outputPath = result.outputPath
        val outputContent = File(outputPath).readText()
        val outputModel = Gson().fromJson(outputContent, ComposeCompilerMetricsExportModel::class.java)

        outputModel.totalModulesScanned shouldBe 2
        outputModel.totalModulesReported shouldBe 1
        outputModel.totalModulesFiltered shouldBe 1
    }
}
