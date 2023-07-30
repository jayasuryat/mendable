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
package com.jayasuryat.mendable

import com.jayasuryat.mendable.metricsfile.MetricsFile
import com.jayasuryat.mendable.scanner.scanForMetricsFiles
import java.io.File

internal fun Any.getTestMetricsFilesFromResources(): List<MetricsFile> {

    val testFilePath: String = javaClass.classLoader
        ?.getResource("app_release-composables.txt")
        ?.path.orEmpty()

    val parentPath: String = File(testFilePath).parent

    return scanForMetricsFiles(parentPath)
}

internal fun Any.readFileAsTextFromResources(fileName: String): String? {
    return this::class.java.classLoader
        ?.getResourceAsStream(fileName)
        ?.bufferedReader()
        ?.readText()
}