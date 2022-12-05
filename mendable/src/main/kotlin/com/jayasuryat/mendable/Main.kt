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

import com.jayasuryat.mendable.html.MendablePage
import com.jayasuryat.mendable.html.saveHtmlFile
import com.jayasuryat.mendable.model.ComposablesReport
import com.jayasuryat.mendable.model.ComposablesReportFile
import com.jayasuryat.mendable.parser.ComposableReportParser
import com.jayasuryat.mendable.parser.getReportFiles

public fun main(args: Array<String>) {

    // Reading and parsing arguments
    val arguments = CliArguments(args = args)

    // Reading files from Disk
    val reportFiles: List<ComposablesReportFile> = getReportFiles(
        path = arguments.composablesReportsPath,
    )

    // Parsing them into workable format
    val parsedReports: List<ComposablesReport> = ComposableReportParser().parse(
        files = reportFiles,
    )

    if (parsedReports.isEmpty()) {
        println()
        println("No composables.txt files found in the directory : ${arguments.composablesReportsPath}")
        println("Make sure to point the application to the directory which contains all the composables.txt files via the '--composablesReportsPath' or '--i' arguments.")
        println("For more help execute the executable with '-h' argument")
        return
    }

    // Generating HTML report from the parsed reports
    val html: String = MendablePage(
        reports = parsedReports,
    )

    // Saving the HTML file
    val savedPath: String = saveHtmlFile(
        htmlContent = html,
        fileName = arguments.outputFileName,
        outputDirectory = arguments.outputPath,
    )

    println("\nOutput file saved at file://$savedPath")
}
