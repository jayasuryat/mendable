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
import java.io.File

internal fun scanForComposeCompilerMetricsFiles(
    directory: File,
    scanRecursively: Boolean,
    postfixes: List<String>,
    moduleFactory: ModuleFactory,
): List<ComposeCompilerMetricsFile> {

    return if (scanRecursively) scanForComposeCompilerMetricsFilesRecursively(
        directory = directory,
        postfixes = postfixes,
        moduleFactory = moduleFactory,
    ) else scanForComposeCompilerMetricsFiles(
        directory = directory,
        postfixes = postfixes,
        moduleFactory = moduleFactory,
    )
}

private fun scanForComposeCompilerMetricsFiles(
    directory: File,
    postfixes: List<String>,
    moduleFactory: ModuleFactory,
): List<ComposeCompilerMetricsFile> {

    if (!directory.exists() || !directory.isDirectory) return emptyList()

    return directory
        .listFiles().orEmpty()
        .filter { file -> file.isFile }
        .filter { file -> file.name.endsWithAny(postfixes) }
        .map { file ->
            requireNotNull(
                ComposeCompilerMetricsFile.from(
                    file = file,
                    moduleFactory = moduleFactory,
                )
            )
        }
}

private fun scanForComposeCompilerMetricsFilesRecursively(
    directory: File,
    postfixes: List<String>,
    moduleFactory: ModuleFactory,
): List<ComposeCompilerMetricsFile> {

    if (!directory.isDirectory) return emptyList()

    val metricsFiles = mutableListOf<ComposeCompilerMetricsFile>()

    directory
        .listFiles().orEmpty()
        .forEach { file: File ->
            when {

                file.isDirectory -> {
                    val files = scanForComposeCompilerMetricsFilesRecursively(file, postfixes, moduleFactory)
                    metricsFiles.addAll(files)
                }

                file.isFile && file.name.endsWithAny(postfixes) -> {
                    val metricsFile: ComposeCompilerMetricsFile = ComposeCompilerMetricsFile.from(
                        file = file,
                        moduleFactory = moduleFactory,
                    )
                    metricsFiles.add(metricsFile)
                }

                else -> Unit /* Do nothing */
            }
        }

    return metricsFiles
}

private fun String.endsWithAny(
    suffixes: List<String>,
    ignoreCase: Boolean = false,
): Boolean {
    return suffixes.any { suffix ->
        this@endsWithAny.endsWith(
            suffix = suffix,
            ignoreCase = ignoreCase,
        )
    }
}
