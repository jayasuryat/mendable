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

import com.jayasuryat.mendable.MendableReportGenerator
import com.jayasuryat.mendable.MendableReportGeneratorRequest
import com.jayasuryat.mendable.app.system.DefaultSystemExit
import com.jayasuryat.mendable.app.system.SystemExit
import kotlinx.coroutines.runBlocking

/**
 * The main entry point of the Mendable application.
 *
 * This function reads and parses command-line arguments, transforms them into a request for generating a mendable report,
 * and then initiates the process of generating the report using the [MendableReportGenerator].
 *
 * @param args The command-line arguments provided to the application.
 *
 * @see [CliArguments]
 * @see [MendableReportGenerator]
 * @see [MendableReportGeneratorRequest]
 */
public fun main(args: Array<String>) {

    val systemExit: SystemExit = DefaultSystemExit()

    // Read and parse command-line arguments
    val arguments = CliArguments(
        args = args,
        systemExit = systemExit,
    )

    // Transform arguments into a request for generating the report
    val request = MendableReportGeneratorRequest(
        scanPaths = arguments.scanPaths,
        scanRecursively = arguments.scanRecursively,
        outputPath = arguments.outputPath,
        outputFileName = arguments.outputFileName,
        exportType = arguments.exportType,
        includeModules = arguments.includeModules,
    )

    val progressPrinter = ProgressPrinter(
        request = request,
    )

    runBlocking {

        // Scan, parse & generate mendable report.
        val result = MendableReportGenerator()
            .generate(request = request) { progress ->
                progressPrinter.print(progress)
            }

        if (result !is MendableReportGenerator.Progress.SuccessfullyCompleted) {
            systemExit.exit(1)
        }
    }
}
