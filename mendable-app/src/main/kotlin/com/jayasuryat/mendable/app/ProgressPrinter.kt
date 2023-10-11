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
package com.jayasuryat.mendable.app

import com.jayasuryat.mendable.MendableReportGenerator.Progress
import com.jayasuryat.mendable.MendableReportGeneratorRequest
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * A class responsible for printing progress messages and errors during the Mendable report generation process.
 *
 * @param request The MendableReportGeneratorRequest containing information about the report generation.
 */
internal class ProgressPrinter(
    private val request: MendableReportGeneratorRequest,
) {

    fun print(progress: Progress) {

        when (progress) {

            is Progress.Initiated -> progress.message().println()

            is Progress.MetricsFilesFound -> progress.message().println()

            is Progress.MetricsFilesParsed -> {
                val (message, errorMessage) = progress.messages()
                message.println()
                errorMessage?.printError()
            }

            is Progress.SuccessfullyCompleted -> progress.message().println()

            is Progress.NoMetricsFilesFound -> progress.message().printError()

            is Progress.Error -> progress.message().printError()
        }
    }

    @Suppress("UnusedReceiverParameter")
    private fun Progress.Initiated.message(): String {

        val message = StringBuilder("Scanning files ${if (request.scanRecursively) "recursively " else ""}")

        if (request.scanPaths.size == 1) {
            message.append("in file:${File.separator}${File.separator}${request.scanPaths.first()} directory\n")
        } else {
            message.append("in the following directories:\n")
            val padStart = request.scanPaths.size.toString().length
            request.scanPaths.forEachIndexed { index, scanPath ->
                val modIndex = (index + 1).toString().padStart(padStart)
                message.append("$modIndex. $scanPath \n")
            }
        }

        return message.toString()
    }

    private fun Progress.MetricsFilesFound.message(): String {
        val builder = StringBuilder()
        builder.appendLine("${files.count()} metrics file(s) found :")
        val padStart = files.size.toString().length
        files.forEachIndexed { index, file ->
            val modIndex = (index + 1).toString().padStart(padStart)
            val module = file.module
            builder.append("$modIndex. :${module.name} (${module.buildVariant}) \n")
        }
        return builder.toString()
    }

    private fun Progress.MetricsFilesParsed.messages(): Pair<String, String?> {

        if (failedToParse == 0) return "All #$parsedSuccessfully metrics files parsed successfully\n" to null

        val message = StringBuilder()
        val errorMessage = StringBuilder()

        message.appendLine("#$parsedSuccessfully files parsed successfully. Failed to parse #$failedToParse files.")
        errorMessage.appendLine("Failed to parse following files:")

        val padStart = errors.size.toString().length
        this.errors.forEachIndexed { index, error ->
            val modIndex = (index + 1).toString().padStart(padStart)
            errorMessage.appendLine("$modIndex. :${error.fileName}")
        }

        return message.toString() to errorMessage.toString()
    }

    @Suppress("UnusedReceiverParameter")
    private fun Progress.NoMetricsFilesFound.message(): String {
        return """
            No composables.txt files found in the following directories : ${request.scanPaths.joinToString()}
            Make sure to point the application to the directory which contains all the composables.txt files via the '--scanPaths' or '--i' argument.
            For more help execute the jar with '-h' argument
        """.trimIndent()
    }

    private fun Progress.SuccessfullyCompleted.message(): String {
        return "$exportType report successfully saved at file:${File.separator}${File.separator}$outputPath"
    }

    private fun Progress.Error.message(): String {
        return "Error : ${this.throwable.asLog()}"
    }
}

/**
 * Convert a Throwable to a log string.
 */
private fun Throwable.asLog(): String {
    val stringWriter = StringWriter(256)
    val printWriter = PrintWriter(stringWriter, false)
    printStackTrace(printWriter)
    printWriter.flush()
    return stringWriter.toString()
}

private fun String.println(): Unit = println(this)
private fun String.printError(): Unit = System.err.println(this)
