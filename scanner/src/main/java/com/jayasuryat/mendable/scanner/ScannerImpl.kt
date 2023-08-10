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
): List<ComposeCompilerMetricsFile> {

    return if (scanRecursively) scanForComposeCompilerMetricsFilesRecursively(
        directory = directory,
        postfixes = postfixes,
    ) else scanForComposeCompilerMetricsFiles(
        directory = directory,
        postfixes = postfixes,
    )
}

private fun scanForComposeCompilerMetricsFiles(
    directory: File,
    postfixes: List<String>,
): List<ComposeCompilerMetricsFile> {

    if (!directory.exists() || !directory.isDirectory) return emptyList()

    return directory
        .listFiles().orEmpty()
        .filter { file -> file.isFile }
        .filter { file -> file.name.endsWithAny(postfixes) }
        .map { file -> requireNotNull(ComposeCompilerMetricsFile.from(file)) }
}

private fun scanForComposeCompilerMetricsFilesRecursively(
    directory: File,
    postfixes: List<String>,
): List<ComposeCompilerMetricsFile> {

    if (!directory.isDirectory) return emptyList()

    val metricsFiles = mutableListOf<ComposeCompilerMetricsFile>()

    directory
        .listFiles().orEmpty()
        .forEach { file: File ->
            when {

                file.isDirectory -> metricsFiles.addAll(scanForComposeCompilerMetricsFilesRecursively(file, postfixes))

                file.isFile && file.name.endsWithAny(postfixes) -> metricsFiles.add(ComposeCompilerMetricsFile.from(file))

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
