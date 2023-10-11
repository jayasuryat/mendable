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
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.metricsfile.Module
import java.io.File

/**
 * Scans the specified path for all Compose compiler metrics files i.e., files with name in following formats:
 * 1. `<module-name>-composables.txt`
 * 2. `<module-name>-classes.txt`
 * 3. `<module-name>-composables.csv`
 * 4. `<module-name>-module.json`
 *
 * @param directory The directory to scan for Compose compiler metrics files.
 * @param scanRecursively Flag to indicate whether to scan the directory recursively or not.
 *                        If set to true, subdirectories will also be scanned (recursively) for metrics files.
 *                        If set to false, only the specified directory will be scanned.
 *                        Default value is false.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 *                      Default is [DefaultModuleFactory].
 * @return A [List] of [ComposeCompilerMetricsFile] objects representing the found metrics files.
 */
public fun scanForAllComposeCompilerMetricsFiles(
    directory: File,
    scanRecursively: Boolean = false,
    moduleFactory: ModuleFactory = DefaultModuleFactory(),
): List<ComposeCompilerMetricsFile> {

    return scanForComposeCompilerMetricsFiles(
        directory = directory,
        scanRecursively = scanRecursively,
        postfixes = allPostfixes,
        moduleFactory = moduleFactory,
    )
}

/**
 * Scans the specified path for all Composable signatures report files. Files with name in `<module-name>-composables.txt`
 * format.
 *
 * @param directory The directory to scan for Composable signatures report files.
 * @param scanRecursively Flag to indicate whether to scan the directory recursively or not.
 *                        If set to true, subdirectories will also be scanned (recursively) for metrics files.
 *                        If set to false, only the specified directory will be scanned.
 *                        Default value is false.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 *                      Default is [DefaultModuleFactory].
 * @return A [List] of [ComposableSignaturesReportFile] objects representing the found metrics files.
 */
public fun scanForComposableSignaturesReportFiles(
    directory: File,
    scanRecursively: Boolean = false,
    moduleFactory: ModuleFactory = DefaultModuleFactory(),
): List<ComposableSignaturesReportFile> {

    return scanForComposeCompilerMetricsFiles(
        directory = directory,
        scanRecursively = scanRecursively,
        postfixes = listOf(COMPOSABLE_SIGNATURES_REPORT_POSTFIX),
        moduleFactory = moduleFactory,
    ).filterIsInstance<ComposableSignaturesReportFile>()
}

/**
 * Scans the specified path for all Class stability report files.  Files with name in `<module-name>-classes.txt`
 *
 * @param directory The directory to scan for Class stability report files.
 * @param scanRecursively Flag to indicate whether to scan the directory recursively or not.
 *                        If set to true, subdirectories will also be scanned (recursively) for metrics files.
 *                        If set to false, only the specified directory will be scanned.
 *                        Default value is false.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 *                      Default is [DefaultModuleFactory].
 * @return A [List] of [ClassStabilityReportFile] objects representing the found metrics files.
 */
public fun scanForClassStabilityReportFiles(
    directory: File,
    scanRecursively: Boolean = false,
    moduleFactory: ModuleFactory = DefaultModuleFactory(),
): List<ClassStabilityReportFile> {

    return scanForComposeCompilerMetricsFiles(
        directory = directory,
        scanRecursively = scanRecursively,
        postfixes = listOf(CLASS_STABILITY_REPORT_POSTFIX),
        moduleFactory = moduleFactory,
    ).filterIsInstance<ClassStabilityReportFile>()
}

/**
 * Scans the specified path for all Composable tabular report files. Files with name in `<module-name>-composables.csv`
 * format.
 *
 * @param directory The directory to scan for Composable tabular report files.
 * @param scanRecursively Flag to indicate whether to scan the directory recursively or not.
 *                        If set to true, subdirectories will also be scanned (recursively) for metrics files.
 *                        If set to false, only the specified directory will be scanned.
 *                        Default value is false.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 *                      Default is [DefaultModuleFactory].
 * @return A [List] of [ComposableTabularReportFile] objects representing the found metrics files.
 */
public fun scanForComposableTabularReportFiles(
    directory: File,
    scanRecursively: Boolean = false,
    moduleFactory: ModuleFactory = DefaultModuleFactory(),
): List<ComposableTabularReportFile> {

    return scanForComposeCompilerMetricsFiles(
        directory = directory,
        scanRecursively = scanRecursively,
        postfixes = listOf(COMPOSABLE_TABULAR_REPORT_POSTFIX),
        moduleFactory = moduleFactory,
    ).filterIsInstance<ComposableTabularReportFile>()
}

/**
 * Scans the specified path for all Composable module metrics files. Files with name in `<module-name>-module.json`
 * format.
 *
 * @param directory The directory to scan for Composable module metrics files.
 * @param scanRecursively Flag to indicate whether to scan the directory recursively or not.
 *                        If set to true, subdirectories will also be scanned (recursively) for metrics files.
 *                        If set to false, only the specified directory will be scanned.
 *                        Default value is false.
 * @param moduleFactory The [ModuleFactory] used to parse the [Module] object from the file name.
 *                      Default is [DefaultModuleFactory].
 * @return A [List] of [ModuleMetricsFile] objects representing the found metrics files.
 */
public fun scanForModuleMetricsFiles(
    directory: File,
    scanRecursively: Boolean = false,
    moduleFactory: ModuleFactory = DefaultModuleFactory(),
): List<ModuleMetricsFile> {

    return scanForComposeCompilerMetricsFiles(
        directory = directory,
        scanRecursively = scanRecursively,
        postfixes = listOf(MODULE_METRICS_POSTFIX),
        moduleFactory = moduleFactory,
    ).filterIsInstance<ModuleMetricsFile>()
}
