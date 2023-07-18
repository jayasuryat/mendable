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

import com.jayasuryat.mendable.metricsfile.MetricsFile

internal fun List<String>.createVariantsWith(
    variants: List<String>,
    transformer: (original: String, variant: String) -> String = { original, variant -> "${original}_$variant" },
): List<String> = this.map { original ->
    variants.map { variant ->
        transformer(original, variant)
    }
}.flatten()

internal fun zipWithVariants(
    names: List<String>,
    variants: List<String>,
    metrics: List<MetricsFile>,
): List<Triple<String, String, MetricsFile>> {

    assert(names.size * variants.size == metrics.size) {
        "The count of names and variants combinations doesn't match size of metrics files. names=#${names.size}, variants=#${variants.size}, metrics=#${metrics.size}."
    }

    return names.map { name -> variants.map { variant -> name to variant } }
        .flatten()
        .sortedBy { (name, variant) -> "${name}_$variant" }
        .zip(metrics) { nameAndVariant: Pair<String, String>, file: MetricsFile ->
            Triple(nameAndVariant.first, nameAndVariant.second, file)
        }
}

internal fun String.composablesTxt(): String = "$this$METRICS_FILE_POSTFIX"
internal fun String.classesTxt(): String = "$this-classes.txt" // TODO: Move to metrics-file module
internal fun String.composablesCsv(): String = "$this-composables.csv" // TODO: Move to metrics-file module
