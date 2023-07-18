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

import com.jayasuryat.mendable.MendableReportGenerator.Progress
import com.jayasuryat.mendable.MendableReportGeneratorRequest.ExportType
import com.jayasuryat.mendable.MendableReportGeneratorRequest.IncludeModules
import kotlinx.coroutines.test.runTest
import org.junit.Assert
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

        Assert.assertTrue(result is Progress.Error)
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

        Assert.assertTrue(result is Progress.Error)
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

        Assert.assertTrue(result is Progress.Error)
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

        Assert.assertTrue(result is Progress.NoMetricsFilesFound)
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

        Assert.assertTrue(result is Progress.SuccessfullyCompleted)
        result as Progress.SuccessfullyCompleted

        Assert.assertEquals("${temporaryFolder.root.path}/report.html", result.outputPath)
        Assert.assertEquals(ExportType.HTML, result.exportType)

        val output = File(result.outputPath)
        Assert.assertTrue(output.exists())
        Assert.assertTrue(output.isFile)
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

        Assert.assertTrue(result is Progress.SuccessfullyCompleted)
        result as Progress.SuccessfullyCompleted

        Assert.assertEquals("${temporaryFolder.root.path}/report.json", result.outputPath)
        Assert.assertEquals(ExportType.JSON, result.exportType)

        val output = File(result.outputPath)
        Assert.assertTrue(output.exists())
        Assert.assertTrue(output.isFile)
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

        Assert.assertTrue(progresses.removeFirst() is Progress.Initiated)
        Assert.assertTrue(progresses.removeFirst() is Progress.MetricsFilesFound)
        Assert.assertTrue(progresses.removeFirst() is Progress.MetricsFilesParsed)
        Assert.assertTrue(progresses.removeFirst() is Progress.SuccessfullyCompleted)
    }
}
