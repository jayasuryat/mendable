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

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ComposableSignaturesReportFile
import com.jayasuryat.mendable.parser.ComposableSignaturesReportFileParser
import com.jayasuryat.mendable.parser.impl.ComposableSignaturesReportFileParserImpl.Companion.DEFAULT_VALUE_IDENTIFIER
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport.ComposableDetails.Parameter

internal class ComposableSignaturesReportFileParserImpl : ComposableSignaturesReportFileParser {

    override fun parse(
        file: ComposableSignaturesReportFile,
    ): ComposableSignaturesReport {

        val composables: List<ComposableSignaturesReport.ComposableDetails> = file.file
            .readText()
            .parseComposables()

        return ComposableSignaturesReport(
            module = file.module,
            composables = composables,
        )
    }

    private fun String.parseComposables(): List<ComposableSignaturesReport.ComposableDetails> {

        val content = this

        // Individual lines
        val lines: List<String> = content
            .split("\n")
            .filter { line -> line.isNotBlank() }

        // List of all the functions in the file
        val functions: MutableList<List<String>> = mutableListOf()
        val functionBuilder = mutableListOf<String>()

        for (line in lines) {
            if (line.contains(FUNCTION_IDENTIFIER) && functionBuilder.isNotEmpty()) {
                functions += functionBuilder.toList()
                functionBuilder.clear()
            }
            functionBuilder += line
        }
        if (functionBuilder.isNotEmpty()) {
            functions += functionBuilder.toList()
            functionBuilder.clear()
        }

        return functions.map { function -> function.parseComposable() }
    }

    private fun List<String>.parseComposable(): ComposableSignaturesReport.ComposableDetails {

        // Individual lines of a function
        val lines: List<String> = this
        val firstLine: String = lines.first()

        val beforeFun = firstLine.requireSubStringBefore(FUNCTION_IDENTIFIER).trim()
        val afterFun = firstLine.requireSubStringAfter(FUNCTION_IDENTIFIER).trim()

        val name = afterFun.take(afterFun.lastIndexOf('(')).trim()
        val isRestartable = beforeFun.contains(RESTARTABLE_IDENTIFIER)
        val isSkippable = beforeFun.contains(SKIPPABLE_IDENTIFIER)
        val isInline = beforeFun.contains(INLINE_IDENTIFIER)

        val params: List<Parameter> = if (lines.size == 1) {
            emptyList()
        } else {
            lines
                .drop(1) // Dropping the function declaration
                .dropLast(1) // Dropping the closing bracket of the function body
                .parseParams()
        }

        return ComposableSignaturesReport.ComposableDetails(
            functionName = name,
            isRestartable = isRestartable,
            isSkippable = isSkippable,
            isInline = isInline,
            params = params,
        )
    }

    private fun List<String>.parseParams(): List<Parameter> {

        val params: MutableList<String> = mutableListOf()

        val paramCollector = StringBuilder()
        for (param in this) {
            // A single param can be declared in multiple-lines
            // Assuming that if a line has ": " then it is a new param declaration.
            if (paramCollector.isNotBlank() && param.contains(": ")) {
                params += paramCollector.toString().replace(oldValue = "\n", newValue = "")
                paramCollector.clear()
            }
            paramCollector.appendLine(param)
        }
        if (paramCollector.isNotBlank()) {
            params.add(paramCollector.toString().replace(oldValue = "\n", newValue = ""))
        }

        val modParams = params
            .filter { param -> param.isNotBlank() }
            .map { param -> param.trim().trimExtraWhiteSpaces() }
            .map { param ->

                val stabilityAndName: String = param.take(param.indexOf(':')).trim()
                val typeAndDefaultValue: String = param.drop(param.indexOf(':') + 1).trim()
                val type = typeAndDefaultValue.extractType(identifier = DEFAULT_VALUE_IDENTIFIER)
                val defaultValue: String? = typeAndDefaultValue
                    .removePrefix(type)
                    .removePrefix(DEFAULT_VALUE_IDENTIFIER)
                    .trim()
                    .takeIf { value -> value.isNotBlank() }

                // Example : "stable modifier: Modifier"
                // Or this could just be "modifier", and the "stable"/"unstable" part could be missing as well
                val splitIndex: Int? = stabilityAndName
                    .trim()
                    .indexOf(' ')
                    .takeIf { it != -1 }

                val condition: Parameter.Condition
                val paramName: String

                if (splitIndex != null) {
                    // Report has stability mentioned for this param
                    val conditionString = stabilityAndName.take(splitIndex)
                    condition =
                        Parameter.Condition.from(
                            conditionString
                        )
                    paramName = stabilityAndName.drop(splitIndex + 1)
                } else {
                    // Report does not have stability mentioned for this param
                    condition = Parameter.Condition.UNKNOWN
                    paramName = stabilityAndName.trim()
                }

                Parameter(
                    condition = condition,
                    name = paramName,
                    type = type,
                    defaultValue = defaultValue,
                )
            }

        return modParams
    }

    private val duplicateWhiteSpaceRegex: Regex by lazy { "\\s+".toRegex() }
    private fun String.trimExtraWhiteSpaces(): String {
        return this.replace(regex = duplicateWhiteSpaceRegex, " ")
    }

    private fun Parameter.Condition.Companion.from(value: String): Parameter.Condition {
        return when (value.lowercase()) {
            "stable" -> Parameter.Condition.STABLE
            "unstable" -> Parameter.Condition.UNSTABLE
            "unused" -> Parameter.Condition.UNUSED
            else -> error("Unable to parse unrecognized value for condition '$value'")
        }
    }

    internal companion object {

        private const val FUNCTION_IDENTIFIER: String = "fun "
        private const val RESTARTABLE_IDENTIFIER: String = "restartable"
        private const val SKIPPABLE_IDENTIFIER: String = "skippable"
        private const val INLINE_IDENTIFIER: String = "inline"

        // Internal for testing, otherwise should have been private
        internal const val DEFAULT_VALUE_IDENTIFIER: String = " = "
    }
}

/**
 * Extract types from type signatures which may have type and their default value in the same string.
 * For example, for "Test<Int> = {}" "Test<Int>" would be returned.
 */
internal fun String.extractType(
    identifier: String = DEFAULT_VALUE_IDENTIFIER,
): String {

    // This property does not have a default value
    if (!this.contains(identifier)) return this

    // This property does not have any generic types, so type extraction is straightforward
    if (!this.contains('<')) return this.take(this.indexOf(identifier))

    // If the generic character is in default value and not in the type
    if (this.indexOf(identifier) < this.indexOf('<')) return this.take(this.indexOf(identifier))

    var symbolStack = 0
    var symbolsReached = false
    this.forEachIndexed { index, char ->

        when (char) {
            '<' -> {
                symbolStack++
                symbolsReached = true
            }

            '>' -> symbolStack--
        }

        if (symbolsReached && symbolStack == 0) {
            val typeEndIndex = this@extractType.indexOf(identifier, startIndex = index)
            return if (typeEndIndex == -1) this.substring(0, index + 1)
            else this.substring(0, typeEndIndex)
        }
    }

    return this
}

private fun String.requireSubStringBefore(
    delimiter: String,
): String {
    val index = this.indexOf(delimiter)
    require(index != -1) { "Line `$this` does not contain delimiter `$delimiter`" }
    return this.substring(0, index)
}

private fun String.requireSubStringAfter(
    delimiter: String,
): String {
    val index = this.indexOf(delimiter)
    require(index != -1) { "Line `$this` does not contain delimiter `$delimiter`" }
    return this.substring(index + delimiter.length, this.length)
}
