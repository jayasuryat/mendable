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
import com.jayasuryat.mendable.app.system.SystemExit
import java.io.PrintWriter
import java.io.StringWriter

internal class ProgressPrinter(
    private val request: MendableReportGeneratorRequest,
    private val systemExit: SystemExit,
) {

    fun print(progress: Progress) {

        val message: String = when (progress) {
            is Progress.Initiated -> progress.message()
            is Progress.MetricsFilesFound -> progress.message()
            is Progress.MetricsFilesParsed -> progress.message()
            is Progress.NoMetricsFilesFound -> progress.message()
            is Progress.SuccessfullyCompleted -> progress.message()
            is Progress.Error -> progress.message()
        }

        println(message)
        if (progress is Progress.Result) systemExit.exit(1)
    }

    @Suppress("UnusedReceiverParameter")
    private fun Progress.Initiated.message(): String {
        return "Inputs have been validated. Scanning files ${if (request.scanRecursively) "recursively " else ""}in ${request.scanPath} directory"
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

    private fun Progress.MetricsFilesParsed.message(): String {
        return if (failedToParse == 0) "All #$parsedSuccessfully metrics files successfully parsed \n"
        else "#$parsedSuccessfully files parsed successfully. Failed to parse #$failedToParse files \n"
    }

    @Suppress("UnusedReceiverParameter")
    private fun Progress.NoMetricsFilesFound.message(): String {
        return """
            No composables.txt files found in the directory : ${request.scanPath}
            Make sure to point the application to the directory which contains all the composables.txt files via the '--composablesReportsPath' or '--i' arguments.
            For more help execute the executable with '-h' argument
        """.trimIndent()
    }

    private fun Progress.SuccessfullyCompleted.message(): String {
        return "$exportType report successfully saved at $outputPath"
    }

    private fun Progress.Error.message(): String {
        return "Error : ${this.throwable.asLog()}"
    }

    private fun Throwable.asLog(): String {
        val stringWriter = StringWriter(256)
        val printWriter = PrintWriter(stringWriter, false)
        printStackTrace(printWriter)
        printWriter.flush()
        return stringWriter.toString()
    }
}
