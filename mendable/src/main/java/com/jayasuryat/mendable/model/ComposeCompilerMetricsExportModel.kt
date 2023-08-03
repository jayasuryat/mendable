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
package com.jayasuryat.mendable.model

import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport

internal data class ComposeCompilerMetricsExportModel(
    val modules: List<ModuleDetails>,
    val overview: Overview,
    val totalModulesScanned: Int,
    val totalModulesReported: Int,
) {

    val totalModulesFiltered: Int = totalModulesScanned - totalModulesReported

    data class ModuleDetails(
        val overview: Overview,
        val report: ComposableSignaturesReport,
    )

    data class Overview(
        val totalComposables: Int,
        val restartableComposables: Int,
        val skippableComposables: Int,
        val skippablePercentage: Int,
    )
}
