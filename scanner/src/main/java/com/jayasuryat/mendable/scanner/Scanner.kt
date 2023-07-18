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

public fun scanForMetricsFiles(
    directory: String,
): List<MetricsFile> {
    return scanForMetricsFiles(
        directory = File(directory),
    )
}

public fun scanForMetricsFiles(
    directory: File,
): List<MetricsFile> {

    if (!directory.exists() || !directory.isDirectory) return emptyList()

    return directory
        .listFiles().orEmpty()
        .filter { file -> file.isFile }
        .filter { file -> file.name.endsWith(METRICS_FILE_POSTFIX) }
        .map { file -> MetricsFile.from(file) }
        .sortedBy { file -> "${file.module.name} ${file.module.buildVariant}" }
}
