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
import kotlinx.html.*

internal fun BODY.Overview(
    report: ComposablesReport,
) {

    val overview = report.overview

    // All modules combined overview
    div("overview-div") {

        +"Scanned ${report.totalModuleCount} ${if (report.totalModuleCount > 1) "modules" else "module"}"
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

    if (report.totalModuleCount > 1) {

        ModuleOverviews(
            moduleReports = report.moduleReports,
        )
    }
}

private fun BODY.ModuleOverviews(
    moduleReports: List<ComposablesReport.ModuleReport>,
) {

    // Individual modules grid
    div("grid-container") {

        moduleReports.forEach { module ->

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

                val overview = module.overview

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
