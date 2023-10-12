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

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile
import com.jayasuryat.mendable.metricsfile.Module
import java.io.File

/**
 * Factory method for creating a [ComposeCompilerMetricsFile] from the provided [File].
 *
 * @param file The [File] representing the Compose compiler metrics file.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 * @return A [ComposeCompilerMetricsFile] instance representing the parsed metrics file.
 * @throws IllegalArgumentException if the file name doesn't match any known Compose-Compiler-Metrics file name signatures.
 */
internal fun ComposeCompilerMetricsFile.Companion.from(
    file: File,
    moduleFactory: ModuleFactory,
): ComposeCompilerMetricsFile {

    val name = file.name

    val module: Module = moduleFactory.parseModule(file)

    val identifier: String = name.drop(name.lastIndexOf("-"))

    val metricsFile: ComposeCompilerMetricsFile = when (identifier) {

        // app_qaRelease-composables.txt
        COMPOSABLE_SIGNATURES_REPORT_POSTFIX -> ComposeCompilerMetricsFile.ComposableSignaturesReportFile(
            file = file,
            module = module,
        )

        // app_qaRelease-classes.txt
        CLASS_STABILITY_REPORT_POSTFIX -> ComposeCompilerMetricsFile.ClassStabilityReportFile(
            file = file,
            module = module,
        )

        // app_qaRelease-composables.csv
        COMPOSABLE_TABULAR_REPORT_POSTFIX -> ComposeCompilerMetricsFile.ComposableTabularReportFile(
            file = file,
            module = module,
        )

        // app_qaRelease-module.json
        MODULE_METRICS_POSTFIX -> ComposeCompilerMetricsFile.ModuleMetricsFile(
            file = file,
            module = module,
        )

        else -> error("File name '$name' does not match any known Compose-Compiler-Metrics file name signatures.")
    }

    return metricsFile
}
