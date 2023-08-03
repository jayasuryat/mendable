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
package com.jayasuryat.mendable.parser.util

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.scanner.scanForClassStabilityReportFiles
import com.jayasuryat.mendable.scanner.scanForComposableSignaturesReportFiles
import com.jayasuryat.mendable.scanner.scanForComposableTabularReportFiles
import com.jayasuryat.mendable.scanner.scanForModuleMetricsFiles
import java.io.File

internal fun Any.getResourceFile(fileName: String): File {

    if (fileName.contains("/")) {

        val root = fileName.take(fileName.indexOf("/"))
        val rest = fileName.removePrefix("$root/")

        val rootFile = getResourceFile(root)

        return File(rootFile.path + "/$rest")
    }

    return this::class.java.classLoader
        ?.getResource(fileName)
        ?.path
        ?.let { File(it) }
        ?: error("No file with name `$fileName` found in resources")
}

internal fun Any.readFileAsTextFromResources(fileName: String): String {
    return getResourceFile(fileName).readText()
}

internal fun Any.getComposableSignaturesReportFileFromResources(
    fileName: String,
): ComposableSignaturesReportFile {

    val parentPath: String = getResourceFile(fileName).parent

    val absoluteFileName = fileName.absoluteFileName()
    return scanForComposableSignaturesReportFiles(parentPath)
        .first { file -> file.file.name == absoluteFileName }
}

internal fun Any.getClassStabilityReportFileFromResources(
    fileName: String,
): ClassStabilityReportFile {

    val parentPath: String = getResourceFile(fileName).parent

    val absoluteFileName = fileName.absoluteFileName()
    return scanForClassStabilityReportFiles(parentPath)
        .first { file -> file.file.name == absoluteFileName }
}

internal fun Any.getComposableTabularReportFileFromResources(
    fileName: String,
): ComposableTabularReportFile {

    val parentPath: String = getResourceFile(fileName).parent

    val absoluteFileName = fileName.absoluteFileName()
    return scanForComposableTabularReportFiles(parentPath)
        .first { file -> file.file.name == absoluteFileName }
}

internal fun Any.getModuleMetricsFileFromResources(
    fileName: String,
): ModuleMetricsFile {

    val parentPath: String = getResourceFile(fileName).parent

    val absoluteFileName = fileName.absoluteFileName()
    return scanForModuleMetricsFiles(parentPath)
        .first { file -> file.file.name == absoluteFileName }
}

private fun String.absoluteFileName(): String {
    val fileName = this
    return if (!fileName.contains("/")) fileName
    else fileName.takeLast(fileName.length - fileName.lastIndexOf("/") - 1)
}
