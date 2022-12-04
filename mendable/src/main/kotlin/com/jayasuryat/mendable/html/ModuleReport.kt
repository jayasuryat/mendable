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

import com.jayasuryat.mendable.model.ComposableDetails
import com.jayasuryat.mendable.model.ComposableDetails.Parameter.Condition
import com.jayasuryat.mendable.model.ComposablesReport
import kotlinx.html.*

internal fun BODY.ModuleReport(
    report: ComposablesReport,
) {

    val restartable = report.composables.filter { composable -> composable.isRestartable }
    val mendable = restartable.filter { composable -> !composable.isSkippable }

    div {

        id = report.module.id

        // Section header
        div("module-report-title") { +":${report.module.displayName}" }

        // Section body
        if (mendable.isNotEmpty()) {

            div("module-report-body") {
                ComposableReport(details = mendable)
            }
        } else {

            div("module-report-empty-body") {
                +"It's all good. All ${restartable.count()} restartable composables are skippable."
            }
        }
    }
}

private fun DIV.ComposableReport(
    details: List<ComposableDetails>,
) {

    details.forEachIndexed { index, composable ->

        // Divider
        if (index >= 1) {
            br { }
            br { }
        }

        Composable(
            index = index + 1,
            composable = composable,
        )
    }
}

private fun DIV.Composable(
    index: Int,
    composable: ComposableDetails,
) {

    span("composable-title") { +"$index. " }

    div("click-to-copy") {
        span("function-name") { +"${composable.functionName} " }
        span("tooltip-text") { +"Click to copy" }
    }

    div("code-container") {
        pre {
            code(classes = "code") {

                span {
                    setStyle(color = CodeColors.annotation)
                    +"@Composable\n"
                }

                span {
                    setStyle(color = CodeColors.reserved)
                    +"${if (composable.isInline) "inline " else ""}fun "
                }

                span {
                    setStyle(color = CodeColors.declaration)
                    +composable.functionName
                }

                if (composable.params.isEmpty()) {

                    span { +"(){...}" }
                } else {

                    span { +"(\n" }

                    composable.params.forEach { param ->

                        span { +"   " }

                        when (param.condition) {

                            Condition.UNSTABLE -> {
                                span {
                                    setStyle(color = CodeColors.error)
                                    +"${param.name}: ${param.type}, // Not stable\n"
                                }
                            }

                            Condition.UNKNOWN -> {
                                span {
                                    setStyle(backgroundColor = CodeColors.warning)
                                    +"${param.name}: ${param.type}, // Stability unknown\n"
                                }
                            }

                            Condition.STABLE,
                            Condition.UNUSED -> {
                                span { +"${param.name}: ${param.type}" }
                                span {
                                    setStyle(color = CodeColors.reserved)
                                    +",\n"
                                }
                            }
                        }
                    }
                    +"){...}"
                }
            }
        }
    }
}
