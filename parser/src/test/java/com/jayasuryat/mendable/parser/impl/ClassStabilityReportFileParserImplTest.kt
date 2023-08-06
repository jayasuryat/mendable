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
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ClassStabilityReportFile
import com.jayasuryat.mendable.parser.ClassStabilityReportFileParser
import com.jayasuryat.mendable.parser.model.ClassStabilityReport
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.ClassDetails.RuntimeStability
import com.jayasuryat.mendable.parser.util.RuntimeStabilityAdapter
import com.jayasuryat.mendable.parser.util.getClassStabilityReportFileFromResources
import com.jayasuryat.mendable.parser.util.readFileAsTextFromResources
import io.kotest.assertions.json.*
import org.junit.Test

internal class ClassStabilityReportFileParserImplTest {

    private val parser: ClassStabilityReportFileParser = ClassStabilityReportFileParserImpl()

    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapter(RuntimeStability::class.java, RuntimeStabilityAdapter())
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    @Test
    fun `ClassStabilityReportFileParserImpl should parse all classes correctly`() {
        // Read and get the report file from test resources folder
        val mapped: ClassStabilityReportFile =
            getClassStabilityReportFileFromResources("class_stability/app_qaRelease-classes.txt")

        // Parsing the mapped values, this is the part being tested
        val report: ClassStabilityReport = parser.parse(mapped)

        val expectedJson = readFileAsTextFromResources("class_stability/expected.json")

        val actual = gson.toJson(report)

        actual shouldEqualJson {
            propertyOrder = PropertyOrder.Lenient
            arrayOrder = ArrayOrder.Lenient
            fieldComparison = FieldComparison.Strict
            numberFormat = NumberFormat.Strict
            typeCoercion = TypeCoercion.Disabled
            expectedJson
        }
    }
}
