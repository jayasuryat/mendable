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
package com.jayasuryat.mendable.scanner

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.rules.TemporaryFolder

internal class ScannerApiTest {

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @BeforeEach
    fun setup() {
        temporaryFolder.create()
    }

    @AfterEach
    fun teardown() {
        temporaryFolder.delete()
    }

    // region : Scan test
    @Test
    fun `should scan ComposableSignaturesReportFile correctly`() {

        val fileName = "discover_release$COMPOSABLE_SIGNATURES_REPORT_POSTFIX"
        val nestedFileName = "trending_release$COMPOSABLE_SIGNATURES_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForComposableSignaturesReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = false
        )

        Assertions.assertEquals(1, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should scan ClassStabilityReportFile correctly`() {

        val fileName = "discover_release$CLASS_STABILITY_REPORT_POSTFIX"
        val nestedFileName = "trending_release$CLASS_STABILITY_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForClassStabilityReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = false
        )

        Assertions.assertEquals(1, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should scan ComposableTabularReportFile correctly`() {

        val fileName = "discover_release$COMPOSABLE_TABULAR_REPORT_POSTFIX"
        val nestedFileName = "trending_release$COMPOSABLE_TABULAR_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForComposableTabularReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = false
        )

        Assertions.assertEquals(1, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should scan ModuleMetricsFile correctly`() {

        val fileName = "discover_release$MODULE_METRICS_POSTFIX"
        val nestedFileName = "trending_release$MODULE_METRICS_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForModuleMetricsFiles(
            directory = temporaryFolder.root,
            scanRecursively = false
        )

        Assertions.assertEquals(1, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }
    // endregion

    // region : Recursive scan tests
    @Test
    fun `should recursively scan ComposableSignaturesReportFile correctly`() {

        val fileName = "discover_release$COMPOSABLE_SIGNATURES_REPORT_POSTFIX"
        val nestedFileName = "trending_release$COMPOSABLE_SIGNATURES_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForComposableSignaturesReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = true,
        )

        Assertions.assertEquals(2, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should recursively scan ClassStabilityReportFile correctly`() {

        val fileName = "discover_release$CLASS_STABILITY_REPORT_POSTFIX"
        val nestedFileName = "trending_release$CLASS_STABILITY_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForClassStabilityReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = true,
        )

        Assertions.assertEquals(2, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should recursively scan ComposableTabularReportFile correctly`() {

        val fileName = "discover_release$COMPOSABLE_TABULAR_REPORT_POSTFIX"
        val nestedFileName = "trending_release$COMPOSABLE_TABULAR_REPORT_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForComposableTabularReportFiles(
            directory = temporaryFolder.root,
            scanRecursively = true,
        )

        Assertions.assertEquals(2, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }

    @Test
    fun `should recursively scan ModuleMetricsFile correctly`() {

        val fileName = "discover_release$MODULE_METRICS_POSTFIX"
        val nestedFileName = "trending_release$MODULE_METRICS_POSTFIX"

        temporaryFolder.newFile(fileName)
        temporaryFolder.newFolder("nest")
        temporaryFolder.newFile("nest/$nestedFileName")

        val reports = scanForModuleMetricsFiles(
            directory = temporaryFolder.root,
            scanRecursively = true,
        )

        Assertions.assertEquals(2, reports.size)

        val report = reports.first()
        Assertions.assertEquals(fileName, report.file.name)
    }
    // endregion

    @Test
    fun `should read correct number of files with details`() {

        val root = "app_qaRelease-composables.txt"
        val nest1 = "feature_a_build_variant-composables.txt"
        val nest2 = "compose_release-classes.txt"

        temporaryFolder.newFile(root)

        temporaryFolder.newFolder("nest1")
        temporaryFolder.newFile("nest1/$nest1")

        temporaryFolder.newFolder("nest/nest2")
        temporaryFolder.newFile("nest/nest2/$nest2")

        val reports: List<ComposeCompilerMetricsFile> = scanForAllComposeCompilerMetricsFiles(
            directory = temporaryFolder.root,
            scanRecursively = false,
        )

        Assertions.assertEquals(1, reports.size)

        val report = reports.first()
        Assertions.assertEquals(root, report.file.name)
        Assertions.assertEquals(temporaryFolder.root.path, report.file.parent)
    }

    @Test
    fun `should read correct number of files with details recursively`() {

        val root = "app_qaRelease-composables.txt"
        val nest1 = "feature_a_build_variant-composables.txt"
        val nest2 = "compose_release-classes.txt"

        temporaryFolder.newFile(root)

        temporaryFolder.newFolder("nest1")
        temporaryFolder.newFile("nest1/$nest1")

        temporaryFolder.newFolder("nest/nest2")
        temporaryFolder.newFile("nest/nest2/$nest2")

        val reports: List<ComposeCompilerMetricsFile> = scanForAllComposeCompilerMetricsFiles(
            directory = temporaryFolder.root,
            scanRecursively = true,
        ).sortedBy { report -> report.file.name }

        Assertions.assertEquals(3, reports.size)

        val expectedFileNames: List<String> = listOf(root, nest1, nest2).sorted()

        reports.zip(expectedFileNames)
            .forEach { (report, name) ->
                Assertions.assertEquals(name, report.file.name)
            }
    }
}
