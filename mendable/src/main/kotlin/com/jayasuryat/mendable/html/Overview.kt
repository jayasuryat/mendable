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
@file:Suppress("FunctionName")

package com.jayasuryat.mendable.html

import com.jayasuryat.mendable.model.ComposablesReport
import com.jayasuryat.mendable.model.Module
import kotlinx.html.*
import kotlin.math.roundToInt

internal fun BODY.Overview(
    reports: List<ComposablesReport>,
) {

    val overview = OverviewModel.from(reports)

    // All modules combined overview
    div("overview-div") {

        +"Scanned ${reports.size} ${if (reports.size > 1) "modules" else "module"}"
        br { +"${overview.totalComposables} composables" }
        br { +"${overview.restartableComposables} restartable" }
        br { +"${overview.skippableComposables} skippable" }
        br { +"${overview.restartableComposables - overview.skippableComposables} not-skippable" }
        br { +"${overview.skippablePercentage}% stable" }
        br { }
        br {
            // Progress bar
            progress {
                value = overview.skippablePercentage.toString()
                max = 100.toString()
            }
        }
    }

    if (overview.moduleOverviews.size > 1) {

        ModuleOverviews(
            overviews = overview.moduleOverviews,
        )
    }
}

private fun BODY.ModuleOverviews(
    overviews: List<OverviewModel.ModuleOverview>,
) {

    // Individual modules grid
    div("grid-container") {

        overviews.forEach { module ->

            // Grid item
            div("grid-item") {

                a(href = "#${module.module.id}") { span(classes = "link-spanner") { +"" } }

                a(classes = "module-overview-title") { +":${module.module.displayName}" }
                br { }
                // TODO: Merge  module-overview-details & grid-item
                br { span("module-overview-details") { +"${module.totalComposables} composables" } }
                br { span("module-overview-details") { +"${module.restartableComposables} restartable" } }
                br { span("module-overview-details") { +"${module.skippableComposables} skippable" } }
                br { span("module-overview-details") { +"${module.skippablePercentage}% stable" } }
                br { }

                br {
                    // Progress bar
                    progress {
                        value = module.skippablePercentage.toString()
                        max = 100.toString()
                    }
                }
            }
        }
    }
}

private data class OverviewModel(
    val totalModules: Int,
    val totalComposables: Int,
    val restartableComposables: Int,
    val skippableComposables: Int,
    val skippablePercentage: Int,
    val moduleOverviews: List<ModuleOverview>,
) {

    data class ModuleOverview(
        val module: Module,
        val totalComposables: Int,
        val restartableComposables: Int,
        val skippableComposables: Int,
        val skippablePercentage: Int,
    )

    companion object {

        fun from(reports: List<ComposablesReport>): OverviewModel {

            val totalModules = reports.size
            val moduleOverviews = reports.map { report ->

                val restartableComposables = report.composables.filter { composable -> composable.isRestartable }

                val skippable = restartableComposables.count { composable -> composable.isSkippable }
                val restartable = restartableComposables.size

                val skippablePercentage = if (restartable == 0) 100
                else ((skippable * 100f) / restartable).roundToInt()

                ModuleOverview(
                    module = report.module,
                    totalComposables = report.composables.size,
                    restartableComposables = restartable,
                    skippableComposables = skippable,
                    skippablePercentage = skippablePercentage,
                )
            }

            val totalComposables = moduleOverviews.sumOf { overview -> overview.totalComposables }
            val restartableComposables = moduleOverviews.sumOf { overview -> overview.restartableComposables }
            val skippableComposables = moduleOverviews.sumOf { overview -> overview.skippableComposables }

            val skippablePercentage = if (restartableComposables == 0) 100
            else ((skippableComposables * 100f) / restartableComposables).roundToInt()

            return OverviewModel(
                totalModules = totalModules,
                totalComposables = totalComposables,
                restartableComposables = restartableComposables,
                skippableComposables = skippableComposables,
                moduleOverviews = moduleOverviews,
                skippablePercentage = skippablePercentage,
            )
        }
    }
}
