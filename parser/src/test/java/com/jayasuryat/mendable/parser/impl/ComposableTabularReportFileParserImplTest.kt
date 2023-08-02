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
package com.jayasuryat.mendable.parser.impl

import com.google.gson.GsonBuilder
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ComposableTabularReportFile
import com.jayasuryat.mendable.parser.ComposableTabularReportFileParser
import com.jayasuryat.mendable.parser.model.ComposableTabularReport
import com.jayasuryat.mendable.parser.util.getComposableTabularReportFileFromResources
import com.jayasuryat.mendable.parser.util.readFileAsTextFromResources
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class ComposableTabularReportFileParserImplTest {

    private val parser: ComposableTabularReportFileParser = ComposableTabularReportFileParserImpl()

    @Test
    fun `ComposableTabularReportFileParserImpl should parse all composables correctly`() {
        // Read and get the report file from test resources folder
        val mapped: ComposableTabularReportFile =
            getComposableTabularReportFileFromResources("composable_tabular/bulky_release-composables.csv")

        // Parsing the mapped values, this is the part being tested
        val report: ComposableTabularReport = parser.parse(mapped)

        val expectedJson = readFileAsTextFromResources("composable_tabular/expected.json")

        val actual = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(report)
        JSONAssert.assertEquals(expectedJson, actual, JSONCompareMode.NON_EXTENSIBLE)
    }
}
