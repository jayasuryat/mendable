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
import java.io.File

public fun scanForMetricsFilesRecursively(
    directory: String,
): List<MetricsFile> {
    return scanForMetricsFilesRecursively(
        directory = File(directory),
    )
}

public fun scanForMetricsFilesRecursively(
    directory: File,
): List<MetricsFile> {

    if (!directory.isDirectory) return emptyList()

    val metricsFiles = mutableListOf<MetricsFile>()

    directory
        .listFiles().orEmpty()
        .forEach { file ->
            when {

                file.isDirectory -> metricsFiles.addAll(scanForMetricsFilesRecursively(file))

                file.isFile && file.name.endsWith(METRICS_FILE_POSTFIX) ->
                    metricsFiles.add(MetricsFile.from(file))

                else -> Unit /* Do nothing */
            }
        }

    return metricsFiles
        .sortedBy { file -> "${file.module.name} ${file.module.buildVariant}" }
}
