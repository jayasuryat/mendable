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
package com.jayasuryat.mendable.scanner

internal const val COMPOSABLE_SIGNATURES_REPORT_POSTFIX: String = "-composables.txt"
internal const val CLASS_STABILITY_REPORT_POSTFIX: String = "-classes.txt"
internal const val COMPOSABLE_TABULAR_REPORT_POSTFIX: String = "-composables.csv"
internal const val MODULE_METRICS_POSTFIX: String = "-module.json"

internal val allPostfixes: List<String> = listOf(
    COMPOSABLE_SIGNATURES_REPORT_POSTFIX,
    CLASS_STABILITY_REPORT_POSTFIX,
    COMPOSABLE_TABULAR_REPORT_POSTFIX,
    MODULE_METRICS_POSTFIX,
)
