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
import com.jayasuryat.mendable.MendableReportGeneratorRequest.ExportType
import com.jayasuryat.mendable.app.system.SystemExit
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

internal class ProgressPrinterTest {

    private val request: MendableReportGeneratorRequest = MendableReportGeneratorRequest(
        scanPath = "",
        outputPath = "",
    )

    private val systemExit: SystemExit = FakeSystemExit()

    private val progressPrinter: ProgressPrinter = ProgressPrinter(
        request = request,
        systemExit = systemExit,
    )

    @Test
    fun `should not exit process on Progress-Result-Initiated`() {

        val progress = Progress.Initiated

        shouldNotThrow<Throwable> {
            progressPrinter.print(progress)
        }
    }

    @Test
    fun `should not exit process on Progress-Result-MetricsFilesFound`() {

        val progress = Progress.MetricsFilesFound(
            files = emptyList(),
        )

        shouldNotThrow<Throwable> {
            progressPrinter.print(progress)
        }
    }

    @Test
    fun `should not exit process on Progress-Result-MetricsFilesParsed`() {

        val progress = Progress.MetricsFilesParsed(
            parsedSuccessfully = 0,
            failedToParse = 0,
        )

        shouldNotThrow<Throwable> {
            progressPrinter.print(progress)
        }
    }

    @Test
    fun `should exit process on Progress-Result-NoMetricsFilesFound`() {

        val progress = Progress.NoMetricsFilesFound

        shouldThrow<FakeSystemExit.FakeSystemExitException> {
            progressPrinter.print(progress)
        }
    }

    @Test
    fun `should exit process on Progress-Result-SuccessfullyCompleted`() {

        val progress = Progress.SuccessfullyCompleted(
            outputPath = "",
            exportType = ExportType.HTML,
        )

        shouldThrow<FakeSystemExit.FakeSystemExitException> {
            progressPrinter.print(progress)
        }
    }

    @Test
    fun `should exit process on Progress-Result-Error`() {

        val progress = Progress.Error(
            throwable = IllegalStateException()
        )

        shouldThrow<FakeSystemExit.FakeSystemExitException> {
            progressPrinter.print(progress)
        }
    }
}
