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

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ComposableTabularReportFile
import com.jayasuryat.mendable.parser.ComposableTabularReportFileParser
import com.jayasuryat.mendable.parser.model.ComposableTabularReport
import com.jayasuryat.mendable.parser.model.ComposableTabularReport.ComposableDetails

internal class ComposableTabularReportFileParserImpl : ComposableTabularReportFileParser {

    override fun parse(
        file: ComposableTabularReportFile,
    ): ComposableTabularReport {
        val content = file.file.readText()
        val composables = content.parseTabularReport()
        return ComposableTabularReport(
            module = file.module,
            composables = composables,
        )
    }

    private fun String.parseTabularReport(): List<ComposableDetails> {

        val lines: List<String> = this.split("\n")
            .filter { line -> line.isNotBlank() }

        return lines.drop(1).map { line -> line.parseRow() }
    }

    private fun String.parseRow(): ComposableDetails {

        val values: List<String> = this.split(",")
            .map { value -> value.trim() }

        return ComposableDetails(
            packageName = values[VALUE_PACKAGE_INDEX],
            name = values[VALUE_NAME_INDEX],
            skippable = values[VALUE_SKIPPABLE_INDEX] == "1",
            restartable = values[VALUE_RESTARTABLE_INDEX] == "1",
            readonly = values[VALUE_READONLY_INDEX] == "1",
            inline = values[VALUE_INLINE_INDEX] == "1",
            isLambda = values[VALUE_IS_LAMBDA_INDEX] == "1",
            hasDefaults = values[VALUE_HAS_DEFAULTS_INDEX] == "1",
            defaultsGroup = values[VALUE_DEFAULTS_GROUP_INDEX].toInt(),
            groups = values[VALUE_GROUPS_INDEX].toInt(),
            calls = values[VALUE_CELLS_INDEX].toInt(),
        )
    }

    internal companion object {

        private const val VALUE_PACKAGE_INDEX: Int = 0
        private const val VALUE_NAME_INDEX: Int = 1
        private const val VALUE_SKIPPABLE_INDEX: Int = 3
        private const val VALUE_RESTARTABLE_INDEX: Int = 4
        private const val VALUE_READONLY_INDEX: Int = 5
        private const val VALUE_INLINE_INDEX: Int = 6
        private const val VALUE_IS_LAMBDA_INDEX: Int = 7
        private const val VALUE_HAS_DEFAULTS_INDEX: Int = 8
        private const val VALUE_DEFAULTS_GROUP_INDEX: Int = 9
        private const val VALUE_GROUPS_INDEX: Int = 10
        private const val VALUE_CELLS_INDEX: Int = 11
    }
}
