/*
 * Copyright 2022 Jaya Surya Thotapalli
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
package com.jayasuryat.mendable.model

internal data class ComposablesReport(
    val moduleReports: List<ModuleReport>,
    val overview: Overview,
) {

    val totalModules: Int = moduleReports.count()

    internal data class ModuleReport(
        val module: Module,
        val overview: Overview,
        val composables: List<ComposableDetails>,
    ) {

        internal data class ComposableDetails(
            val functionName: String,
            val isRestartable: Boolean,
            val isSkippable: Boolean,
            val isInline: Boolean,
            val params: List<Parameter>,
        ) {

            data class Parameter(
                val condition: Condition,
                val name: String,
                val type: String,
            ) {

                enum class Condition {
                    STABLE,
                    UNSTABLE,
                    UNUSED,
                    UNKNOWN;
                }
            }
        }
    }

    data class Overview(
        val totalComposables: Int,
        val restartableComposables: Int,
        val skippableComposables: Int,
        val skippablePercentage: Int,
    )
}
