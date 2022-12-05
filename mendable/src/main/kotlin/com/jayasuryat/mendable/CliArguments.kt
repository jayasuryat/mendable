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
package com.jayasuryat.mendable

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess

internal class CliArguments(
    args: Array<String>,
    defaultReadPath: String = Paths.get("").absolutePathString(),
    defaultWritePath: String = Paths.get("").absolutePathString(),
    defaultFileName: String = "index",
) {

    private val parser = ArgParser("Mendable")

    val composablesReportsPath: String by parser.option(
        type = ArgType.String,
        fullName = "composablesReportsPath",
        shortName = "i",
        description = "Path to the directory containing all of the composables.txt files",
    ).default(defaultReadPath)

    val outputPath: String by parser.option(
        type = ArgType.String,
        fullName = "htmlOutputPath",
        shortName = "o",
        description = "HTML output directory",
    ).default(defaultWritePath)

    val outputFileName: String by parser.option(
        type = ArgType.String,
        fullName = "outputName",
        shortName = "oName",
        description = "Name of the output HTML file",
    ).default(defaultFileName)

    init {
        parser.parse(args)
        validateInputs()
    }

    private fun validateInputs(): Boolean {

        fun message(paramName: String): String {
            return "$paramName cannot be empty. Either pass an appropriate value or skip passing a value to conform to the default value."
        }

        if (composablesReportsPath.isBlank()) printErrorAndExit(message("--composablesReportsPath or -i"))
        if (outputPath.isBlank()) printErrorAndExit(message("--outputPath or -o"))
        if (outputFileName.isBlank()) printErrorAndExit(message("--outputFileName or -oName"))

        val input = File(composablesReportsPath)
        if (!input.exists()) printErrorAndExit("Directory $input does not exist")
        if (!input.isDirectory) printErrorAndExit("$input is not a directory")

        val output = File(outputPath)
        if (!output.exists()) printErrorAndExit("Directory $output does not exist")
        if (!output.isDirectory) printErrorAndExit("$output is not a directory")

        return true
    }

    private fun printErrorAndExit(
        message: String,
        exitCode: Int = 1,
    ): Nothing {
        println("\nError: $message\n")
        exitProcess(exitCode)
    }
}
