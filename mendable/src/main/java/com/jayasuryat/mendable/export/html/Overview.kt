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

package com.jayasuryat.mendable.export.html

import com.jayasuryat.mendable.model.ComposeCompilerMetricsExportModel
import kotlinx.html.*

internal fun BODY.Overview(
    report: ComposeCompilerMetricsExportModel,
) {

    val overview = report.overview

    // All modules combined overview
    div("overview-div") {

        +"${report.totalModulesScanned} ${if (report.totalModulesScanned > 1) "modules" else "module"} scanned"

        if (report.totalModulesScanned != report.totalModulesReported) {
            br { +"${report.totalModulesReported}  ${if (report.totalModulesReported > 1) "modules" else "module"} reported" }
            br { +"${report.totalModulesFiltered}  ${if (report.totalModulesFiltered > 1) "modules" else "module"} filtered" }
        }

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

    if (report.totalModulesReported > 1) {

        ModuleOverviews(
            moduleReports = report.modules,
        )
    }
}

private fun BODY.ModuleOverviews(
    moduleReports: List<ComposeCompilerMetricsExportModel.ModuleDetails>,
) {

    // Individual modules grid
    div("grid-container") {

        moduleReports.forEach { moduleDetails ->

            val module = moduleDetails.report

            // Grid item
            div("grid-item") {

                a(href = "#${module.module.id}") { span(classes = "link-spanner") { +"" } }

                span("module-overview-title") { +":${module.module.name}\n" }
                br {
                    span("module-overview-details") {
                        setStyle(fontSize = "18px")
                        +"(${module.module.buildVariant})"
                    }
                }

                val overview = moduleDetails.overview

                br { }
                br { span("module-overview-details") { +"${overview.totalComposables} composables" } }
                br { span("module-overview-details") { +"${overview.restartableComposables} restartable" } }
                br { span("module-overview-details") { +"${overview.skippableComposables} skippable" } }
                br { span("module-overview-details") { +"${overview.restartableComposables - overview.skippableComposables} not-skippable" } }
                br { span("module-overview-details") { +"${overview.skippablePercentage}% stable" } }
                br { }

                br {
                    // Progress bar
                    progress {
                        value = overview.skippablePercentage.toString()
                        max = 100.toString()
                    }
                }
            }
        }
    }
}
