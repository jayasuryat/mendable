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
import java.nio.file.Paths
import kotlin.io.path.absolutePathString

public fun main() {

    println("Mendable CLI says hello to you!")

    // TODO: Read from args
    val path: String = Paths.get("").absolutePathString()
    val outputDirectory: String = Paths.get("").absolutePathString()
    println("Reading files from $path")

    // Reading files from Disk
    val reportFiles: List<ComposablesReportFile> = getReportFiles(path = path)

    // Parsing them into workable format
    val parsedReports: List<ComposablesReport> = ComposableReportParser().parse(
        files = reportFiles,
    )

    // Generating HTML report from the parsed reports
    val html = MendablePage(
        reports = parsedReports,
    )

    // Saving the HTML file
    val savedPath = saveHtmlFile(
        htmlContent = html,
        fileName = "index",
        outputDirectory = outputDirectory,
    )

    println("Output file saved at $savedPath")
}
