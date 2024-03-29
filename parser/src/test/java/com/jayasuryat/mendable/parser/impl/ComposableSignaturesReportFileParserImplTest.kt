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
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ComposableSignaturesReportFile
import com.jayasuryat.mendable.parser.ComposableSignaturesReportFileParser
import com.jayasuryat.mendable.parser.impl.ComposableSignaturesReportFileParserImpl.Companion.DEFAULT_VALUE_IDENTIFIER
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport
import com.jayasuryat.mendable.parser.util.getComposableSignaturesReportFileFromResources
import com.jayasuryat.mendable.parser.util.readFileAsTextFromResources
import io.kotest.assertions.json.*
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class ComposableSignaturesReportFileParserImplTest {

    private val parser: ComposableSignaturesReportFileParser = ComposableSignaturesReportFileParserImpl()

    @Test
    fun `ComposableSignaturesReportFileParserImpl should parse all composables correctly`() {
        // Read and get the report file from test resources folder
        val mapped: ComposableSignaturesReportFile =
            getComposableSignaturesReportFileFromResources("composable_signature/app_release-composables.txt")

        // Parsing the mapped values, this is the part being tested
        val report: ComposableSignaturesReport = parser.parse(mapped)

        val expectedJson = readFileAsTextFromResources("composable_signature/expected.json")

        val actual = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(report)

        actual shouldEqualJson {
            propertyOrder = PropertyOrder.Lenient
            arrayOrder = ArrayOrder.Lenient
            fieldComparison = FieldComparison.Strict
            numberFormat = NumberFormat.Strict
            typeCoercion = TypeCoercion.Disabled
            expectedJson
        }
    }

    @Test
    fun `extract type appropriately from strings with default types`() {

        val input = listOf(
            "Test<Int> = {}",
            "Test = {}",
            "Function1<@[ParameterName(name = 'showId')] Long, Unit>? = @static { it: Long -> }",
            "Function1<@[ParameterName(name = 'id')] Long, Unit>",
            "Function2<@[ParameterName(name = 'startDate')] LocalDate, @[ParameterName(name = 'endDate')] LocalDate?, Unit>",
            "Function3<@[ParameterName(name = 'showId')] Long, @[ParameterName(name = 'seasonId')] Long?, @[ParameterName(name = 'episodeId')] Long?, Unit>",
            "@[ExtensionFunctionType] Function3<RowScope, Composer, Int, Unit>? = @static ComposableSingletons\$DiscoverKt.lambda-6",
            "@[ExtensionFunctionType] Function3<RowScope, Composer, Int, Unit>",
            "Color = @dynamic MaterialTheme.<get-colorScheme>(\$composer, MaterialTheme.\$stable).surface",
        )

        val expected = listOf(
            "Test<Int>",
            "Test",
            "Function1<@[ParameterName(name = 'showId')] Long, Unit>?",
            "Function1<@[ParameterName(name = 'id')] Long, Unit>",
            "Function2<@[ParameterName(name = 'startDate')] LocalDate, @[ParameterName(name = 'endDate')] LocalDate?, Unit>",
            "Function3<@[ParameterName(name = 'showId')] Long, @[ParameterName(name = 'seasonId')] Long?, @[ParameterName(name = 'episodeId')] Long?, Unit>",
            "@[ExtensionFunctionType] Function3<RowScope, Composer, Int, Unit>?",
            "@[ExtensionFunctionType] Function3<RowScope, Composer, Int, Unit>",
            "Color",
        )

        input.zip(expected)
            .forEach { (input: String, expected: String) ->
                val output = input.extractType(identifier = DEFAULT_VALUE_IDENTIFIER)
                output shouldBe expected
            }
    }
}
