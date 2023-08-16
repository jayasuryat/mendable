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
package com.jayasuryat.mendable.parser

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.parser.util.getClassStabilityReportFileFromResources
import com.jayasuryat.mendable.parser.util.getComposableSignaturesReportFileFromResources
import com.jayasuryat.mendable.parser.util.getComposableTabularReportFileFromResources
import com.jayasuryat.mendable.parser.util.getModuleMetricsFileFromResources
import io.kotest.assertions.throwables.shouldNotThrow
import org.junit.Test

class ComposeCompilerMetricsFileParserTest {

    private val parser: ComposeCompilerMetricsFileParser = getComposeCompilerMetricsFileParser()

    @Test
    fun `ComposeCompilerMetricsFileParser should be able to parse all type of report files`() {

        val composableSignaturesReportFile: ComposableSignaturesReportFile =
            getComposableSignaturesReportFileFromResources("composable_signature/app_release-composables.txt")

        val classStabilityReportFileFrom: ClassStabilityReportFile =
            getClassStabilityReportFileFromResources("class_stability/app_qaRelease-classes.txt")

        val composableTabularReportFile: ComposableTabularReportFile =
            getComposableTabularReportFileFromResources("composable_tabular/bulky_release-composables.csv")

        val moduleMetricsFile: ModuleMetricsFile =
            getModuleMetricsFileFromResources("module_metrics/app_qaRelease-module.json")

        shouldNotThrow<Throwable> {
            parser.parse(composableSignaturesReportFile)
            parser.parse(classStabilityReportFileFrom)
            parser.parse(composableTabularReportFile)
            parser.parse(moduleMetricsFile)
        }
    }
}
