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

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ClassStabilityReportFile
import com.jayasuryat.mendable.parser.ClassStabilityReportFileParser
import com.jayasuryat.mendable.parser.model.ClassStabilityReport
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.ClassDetails
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.ClassDetails.Field
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.ClassDetails.Field.DeclarationType
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.ClassDetails.RuntimeStability
import com.jayasuryat.mendable.parser.model.ClassStabilityReport.Condition

internal class ClassStabilityReportFileParserImpl : ClassStabilityReportFileParser {

    override fun parse(
        file: ClassStabilityReportFile,
    ): ClassStabilityReport {

        val content: String = file.file.readText()
        val classes: List<ClassDetails> = content.parseClasses()

        return ClassStabilityReport(
            module = file.module,
            classes = classes,
        )
    }

    // region : Parsers
    private fun String.parseClasses(): List<ClassDetails> {

        val lines: List<String> = this
            .split("\n")
            .filter { line -> line.isNotBlank() }

        val classes: MutableList<List<String>> = mutableListOf()
        val classBuilder: MutableList<String> = mutableListOf()

        lines.forEach { line ->
            if (line.contains(CLASS_IDENTIFIER) && classBuilder.isNotEmpty()) {
                classes += classBuilder.toList()
                classBuilder.clear()
            }
            classBuilder += line
        }

        if (classBuilder.isNotEmpty()) {
            classes += classBuilder.toList()
            classBuilder.clear()
        }

        return classes.map { clazz -> clazz.parseClass() }
    }

    private fun List<String>.parseClass(): ClassDetails {

        val lines: List<String> = this
        val declarationBranches: List<String> = lines.first()
            .split(' ')
            .map { branch -> branch.trim() }

        val condition: Condition = Condition.from(declarationBranches[0])
        val name: String = declarationBranches[2]
        val runtimeStabilityStr: String = lines[lines.size - 2].trim()
        val runtimeStability: RuntimeStability? = RuntimeStability.from(runtimeStabilityStr)

        val fieldLines: List<String> = lines
            .drop(1) // Dropping the class declaration
            .dropLast(1) // Dropping the closing `}`
            .let { fieldLines ->
                // Removing runtime stability information from field related lines
                if (fieldLines.isNotEmpty() && fieldLines.last().contains(RUNTIME_STABILITY_IDENTIFIER))
                    fieldLines.dropLast(1)
                else fieldLines
            }

        return ClassDetails(
            name = name,
            condition = condition,
            runtimeStability = runtimeStability,
            fields = fieldLines.parseFields()
        )
    }

    private fun List<String>.parseFields(): List<Field> {

        return this.map { line ->

            val branches = line
                .split(' ')
                .map { branch -> branch.trim() }
                .filter { it.isNotBlank() }

            val name = branches[2].dropLast(1) // Removing trailing `:`
            val type = branches.takeLast(branches.size - 3).joinToString(separator = " ")

            val condition = Condition.from(branches[0])
            val declarationType = DeclarationType.from(branches[1])

            Field(
                name = name,
                condition = condition,
                declarationType = declarationType,
                type = type,
            )
        }
    }
    // endregion

    // region : Factories
    private fun Condition.Companion.from(
        condition: String,
    ): Condition {

        return when (condition) {
            STABLE_CONDITION_IDENTIFIER -> Condition.Stable
            UNSTABLE_CONDITION_IDENTIFIER -> Condition.Unstable
            RUNTIME_CONDITION_IDENTIFIER -> Condition.Runtime
            else -> error("Condition `$condition` does not match any known Stability Condition types.")
        }
    }

    private fun DeclarationType.Companion.from(
        type: String,
    ): DeclarationType {

        return when (type) {
            "val" -> DeclarationType.Val
            "var" -> DeclarationType.Var
            else -> error("DeclarationType `$type` does not match any known Declaration Types. The value should either be `val` or `var`.")
        }
    }

    private fun RuntimeStability.Companion.from(
        stability: String,
    ): RuntimeStability? {

        return when {
            stability == RUNTIME_STABILITY_STABLE_IDENTIFIER -> RuntimeStability.Stable
            stability == RUNTIME_STABILITY_UNSTABLE_IDENTIFIER -> RuntimeStability.Unstable
            stability.startsWith(RUNTIME_STABILITY_UNCERTAIN_IDENTIFIER) -> RuntimeStability.Uncertain(
                cause = stability
                    .removePrefix(RUNTIME_STABILITY_UNCERTAIN_IDENTIFIER)
                    .dropLast(1)
            )
            stability.startsWith(RUNTIME_STABILITY_PARAMETER_IDENTIFIER) -> RuntimeStability.Parameter(
                cause = stability
                    .removePrefix(RUNTIME_STABILITY_PARAMETER_IDENTIFIER)
                    .dropLast(1)
            )

            else -> null
        }
    }
    // endregion

    internal companion object {

        private const val CLASS_IDENTIFIER: String = " class "
        private const val STABLE_CONDITION_IDENTIFIER: String = "stable"
        private const val UNSTABLE_CONDITION_IDENTIFIER: String = "unstable"
        private const val RUNTIME_CONDITION_IDENTIFIER: String = "runtime"
        private const val RUNTIME_STABILITY_IDENTIFIER: String = "<runtime stability> ="
        private const val RUNTIME_STABILITY_STABLE_IDENTIFIER: String = "<runtime stability> = Stable"
        private const val RUNTIME_STABILITY_UNSTABLE_IDENTIFIER: String = "<runtime stability> = Unstable"
        private const val RUNTIME_STABILITY_UNCERTAIN_IDENTIFIER: String = "<runtime stability> = Uncertain("
        private const val RUNTIME_STABILITY_PARAMETER_IDENTIFIER: String = "<runtime stability> = Parameter("
    }
}
