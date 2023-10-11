/*
 * Copyright 2022 Jaya Surya Thotapalli
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
package com.jayasuryat.mendable.app

import com.jayasuryat.mendable.MendableReportGeneratorRequest.ExportType
import com.jayasuryat.mendable.MendableReportGeneratorRequest.IncludeModules
import com.jayasuryat.mendable.app.system.SystemExit
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.delimiter
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.absolutePathString

/**
 * Represents a class that manages command-line arguments for the Mendable report generation.
 *
 * This class parses command-line arguments and validates them for generating a Mendable report.
 *
 * @param args The array of command-line arguments.
 * @param systemExit The implementation of the [SystemExit] interface for handling system-exits.
 * @param defaultReadPath The default path for reading composables.txt files.
 * @param defaultWritePath The default path for writing the report.
 * @param defaultFileName The default name for the output report file.
 */
internal class CliArguments(
    args: Array<String>,
    private val systemExit: SystemExit,
    defaultReadPath: String = Paths.get("").absolutePathString(),
    defaultWritePath: String = Paths.get("").absolutePathString(),
    defaultFileName: String = "index",
) {

    private val parser = ArgParser("Mendable")

    /** Paths to the directories containing the composables.txt files. */
    val scanPaths: List<String> by parser.option(
        type = ArgType.String,
        fullName = "scanPaths",
        shortName = "i",
        description = "Paths to the directories containing the composables.txt files (for multiple paths use \"path1 path2\" format)",
    ).delimiter(" ")
        .default(listOf(defaultReadPath))

    /** Indicates whether to scan the directory recursively. */
    val scanRecursively: Boolean by parser.option(
        type = ArgType.Boolean,
        fullName = "scanRecursively",
        shortName = "sr",
        description = "Should scan the directory recursively",
    ).default(false)

    /** The output directory for the generated report. */
    val outputPath: String by parser.option(
        type = ArgType.String,
        fullName = "outputPath",
        shortName = "o",
        description = "Output directory",
    ).default(defaultWritePath)

    /** The name of the output file. */
    val outputFileName: String by parser.option(
        type = ArgType.String,
        fullName = "outputName",
        shortName = "oName",
        description = "Name of the output file",
    ).default(defaultFileName)

    /** The type of export for the report (HTML or JSON). */
    val exportType: ExportType by parser.option(
        type = ArgType.Choice(toVariant = { ExportType.find(it) }, toString = { it.name }),
        fullName = "exportType",
        shortName = "eType",
        description = "Type of the export",
    ).default(ExportType.HTML)

    /** The inclusion criteria for modules in the report (ALL or WITH_WARNINGS). */
    val includeModules: IncludeModules by parser.option(
        type = ArgType.Choice(toVariant = { IncludeModules.find(it) }, toString = { it.name }),
        fullName = "reportType",
        shortName = "rType",
        description = "Type of the report",
    ).default(IncludeModules.WITH_WARNINGS)

    init {
        parser.parse(args)
        validateInputs()
    }

    private fun validateInputs() {

        fun message(paramName: String): String {
            return "$paramName cannot be empty. Either pass an appropriate value or skip passing a value to conform to the default value."
        }

        if (scanPaths.isEmpty()) printErrorAndExit(message("--scanPaths or -i"))
        val areAllPathsEmpty = scanPaths.all { scanPath -> scanPath.isBlank() }
        if (areAllPathsEmpty) printErrorAndExit(message("--scanPaths or -i"))

        scanPaths.forEach { scanPath ->
            val input = File(scanPath)
            if (!input.exists()) printErrorAndExit("Directory $scanPath does not exist")
            if (!input.isDirectory) printErrorAndExit("$scanPath is not a directory")
        }

        if (outputPath.isBlank()) printErrorAndExit(message("--outputPath or -o"))
        if (outputFileName.isBlank()) printErrorAndExit(message("--outputFileName or -oName"))

        val output = File(outputPath)
        if (!output.exists()) printErrorAndExit("Directory $output does not exist")
        if (!output.isDirectory) printErrorAndExit("$output is not a directory")
    }

    private fun printErrorAndExit(
        message: String,
        exitCode: Int = 1,
    ): Nothing {
        System.err.println("\nError: $message\n")
        systemExit.exit(exitCode)
    }
}
