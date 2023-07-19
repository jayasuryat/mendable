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

import com.jayasuryat.mendable.metricsfile.MetricsFile
import com.jayasuryat.mendable.parser.model.ComposablesReport
import com.jayasuryat.mendable.parser.model.ComposablesReport.ModuleReport
import com.jayasuryat.mendable.parser.model.ComposablesReport.ModuleReport.ComposableDetails
import com.jayasuryat.mendable.parser.model.ComposablesReport.ModuleReport.ComposableDetails.Parameter
import com.jayasuryat.mendable.parser.model.ComposablesReport.ModuleReport.ComposableDetails.Parameter.Condition
import com.jayasuryat.mendable.parser.model.ComposablesReport.Overview
import kotlin.math.roundToInt

internal class ComposableReportParser : Parser {

    override fun parse(files: List<MetricsFile>): ComposablesReport {

        val moduleReports: List<ModuleReport> = files.map { file -> parse(file) }
        val overview: Overview = moduleReports
            .flatMap { report -> report.composables }
            .toOverview()

        return ComposablesReport(
            moduleReports = moduleReports,
            overview = overview,
            totalModulesScanned = moduleReports.size,
            totalModulesReported = moduleReports.size,
        )
    }

    private fun parse(
        file: MetricsFile,
    ): ModuleReport {

        // Individual lines
        val lines: List<String> = file.content
            .split("\n")
            .filter { line -> line.isNotBlank() }

        // List of all the functions in the file
        val functions: MutableList<String> = mutableListOf()

        val functionBuilder = StringBuilder()
        for (line in lines) {
            if (line.contains(FUNCTION_IDENTIFIER)) {
                if (functionBuilder.isNotBlank()) functions.add(functionBuilder.toString())
                functionBuilder.clear()
            }
            functionBuilder.appendLine(line)
        }
        if (functionBuilder.isNotBlank()) functions.add(functionBuilder.toString())

        // TODO : Handle parsing exceptions
        val composables = functions.map { function -> function.parseFunction() }

        return ModuleReport(
            module = file.module,
            composables = composables,
            overview = composables.toOverview(),
        )
    }

    private fun String.parseFunction(): ComposableDetails {

        fun String.splitFunctionIntoLines(): List<String> {

            val lines = this.split("\n")
                .filter { line -> line.isNotBlank() }
                .map { line -> line.trim() }

            val modFunction: MutableList<String> = mutableListOf()

            // TODO: Flatten multi-line param signature into a single line
            for (line in lines) {
                // Merging lines which only have closing curly brace of a lambda into previous line
                if (line.trim() == "}") {
                    val last = modFunction.removeLast()
                    modFunction.add("$last}")
                } else {
                    modFunction.add(line)
                }
            }

            return modFunction
        }

        // Individual lines of a function
        val lines: List<String> = this.splitFunctionIntoLines()

        val firstLine = lines.first()

        val beforeFun = firstLine.substringBefore(FUNCTION_IDENTIFIER).trim()
        val afterFun = firstLine.substringAfter(FUNCTION_IDENTIFIER).trim()

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

        return ComposableDetails(
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
            if (param.contains(": ")) {
                params += paramCollector.toString().replace(oldValue = "\n", newValue = "")
                paramCollector.clear()
            }
            paramCollector.appendLine(param)
        }
        if (paramCollector.isNotBlank()) {
            params.add(paramCollector.toString().replace(oldValue = "\n", newValue = ""))
        }

        return params
            .filter { param -> param.isNotEmpty() }
            .map { param -> param.trimExtraWhiteSpaces() }
            .map { param ->

                val stabilityAndName: String = param.take(param.indexOf(':')).trim()
                val type: String = param.drop(param.indexOf(':') + 1).trim()

                // Example : "stable modifier: Modifier"
                // Or this could just be "modifier", and the "stable"/"unstable" part could be missing as well
                val splitIndex: Int? = stabilityAndName
                    .trim()
                    .indexOf(' ')
                    .takeIf { it != -1 }

                val condition: Condition
                val paramName: String

                if (splitIndex != null) {
                    // Report has stability mentioned for this param
                    val conditionString = stabilityAndName.take(splitIndex)
                    condition = Condition.from(conditionString)
                    paramName = stabilityAndName.drop(splitIndex + 1)
                } else {
                    // Report does not have stability mentioned for this param
                    condition = Condition.UNKNOWN
                    paramName = stabilityAndName.trim()
                }

                Parameter(
                    condition = condition,
                    name = paramName,
                    type = type,
                )
            }
    }

    private val duplicateWhiteSpaceRegex: Regex by lazy { "\\s+".toRegex() }
    private fun String.trimExtraWhiteSpaces(): String {
        return this.replace(regex = duplicateWhiteSpaceRegex, " ")
    }

    private fun List<ComposableDetails>.toOverview(): Overview {

        val allComposables = this
        val restartable = allComposables.filter { composable -> composable.isRestartable }
        val skippable = restartable.filter { composable -> composable.isSkippable }

        val percentage: Int = if (restartable.isEmpty()) 100
        else ((skippable.count() * 100f) / restartable.count()).roundToInt()

        return Overview(
            totalComposables = allComposables.count(),
            restartableComposables = restartable.count(),
            skippableComposables = skippable.count(),
            skippablePercentage = percentage,
        )
    }

    private fun Condition.Companion.from(value: String): Condition {
        return when (value.lowercase()) {
            "stable" -> Condition.STABLE
            "unstable" -> Condition.UNSTABLE
            "unused" -> Condition.UNUSED
            else -> error("Unable to parse unrecognized value for condition '$value'")
        }
    }

    internal companion object {

        private const val FUNCTION_IDENTIFIER: String = "fun "
        private const val RESTARTABLE_IDENTIFIER: String = "restartable"
        private const val SKIPPABLE_IDENTIFIER: String = "skippable"
        private const val INLINE_IDENTIFIER: String = "inline"
    }
}
