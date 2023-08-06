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
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test
import java.io.File

internal class ComposeCompilerMetricsFileFactoryTest {

    private val moduleFactory: ModuleFactory = DefaultModuleFactory()

    @Test
    fun `should create metrics file correctly for app_qaRelease-composables-txt file`() {

        val fileName = "app_qaRelease-composables.txt"
        val file = File(fileName)

        val produced: ComposeCompilerMetricsFile = ComposeCompilerMetricsFile.from(
            file = file,
            moduleFactory = moduleFactory,
        )

        produced.shouldBeInstanceOf<ComposeCompilerMetricsFile.ComposableSignaturesReportFile>()
    }

    @Test
    fun `should create metrics file correctly for compose_release-classes-txt file`() {

        val fileName = "compose_release-classes.txt"
        val file = File(fileName)

        val produced: ComposeCompilerMetricsFile = ComposeCompilerMetricsFile.from(
            file = file,
            moduleFactory = moduleFactory,
        )

        produced.shouldBeInstanceOf<ComposeCompilerMetricsFile.ClassStabilityReportFile>()
    }

    @Test
    fun `should create metrics file correctly for discover_release-composables-csv file`() {

        val fileName = "discover_release-composables.csv"
        val file = File(fileName)

        val produced: ComposeCompilerMetricsFile = ComposeCompilerMetricsFile.from(
            file = file,
            moduleFactory = moduleFactory,
        )

        produced.shouldBeInstanceOf<ComposeCompilerMetricsFile.ComposableTabularReportFile>()
    }

    @Test
    fun `should create metrics file correctly for search_release-module-json file`() {

        val fileName = "search_release-module.json"
        val file = File(fileName)

        val produced: ComposeCompilerMetricsFile = ComposeCompilerMetricsFile.from(
            file = file,
            moduleFactory = moduleFactory,
        )

        produced.shouldBeInstanceOf<ComposeCompilerMetricsFile.ModuleMetricsFile>()
    }

    @Test
    fun `should produce error for search_release-module-test-json file`() {

        val fileName = "search_release-module-test.json"
        val file = File(fileName)

        shouldThrow<IllegalStateException> {
            ComposeCompilerMetricsFile.from(
                file = file,
                moduleFactory = moduleFactory,
            )
        }
    }
}
