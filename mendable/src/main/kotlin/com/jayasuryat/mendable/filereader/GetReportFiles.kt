/*
 * Copyright 2022 Jaya Surya Thotapalli
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
package com.jayasuryat.mendable.filereader

import com.jayasuryat.mendable.model.ComposeMetricFile
import com.jayasuryat.mendable.model.Module
import java.io.File

internal fun getReportFiles(
    path: String,
): List<ComposeMetricFile> {

    println()
    println("Reading files at path $path")

    val rootFile = File(path)

    return findComposeFiles(rootFile).map { file ->

        // Example file name : compose_release-composables.txt

        val name = file.name
        val nameIndex = name.indexOf('_')
        val moduleName = name.take(nameIndex)
        val buildVariant = name.subSequence(
            startIndex = nameIndex + 1,
            endIndex = name.length - METRICS_FILE_POSTFIX.length,
        ).toString()

        ComposeMetricFile(
            file = file,
            module = Module(
                name = moduleName,
                buildVariant = buildVariant,
            ),
        )
    }.also(::logDetails)
}

private fun findComposeFiles(directory: File): List<File> {
    if (!directory.isDirectory) {
        return emptyList()
    }
    val list = mutableListOf<File>()
    directory.listFiles().orEmpty().forEach { file ->
        when {
            file.isDirectory -> list.addAll(findComposeFiles(file))
            file.isFile && file.name.endsWith(METRICS_FILE_POSTFIX) -> list.add(file)
            else -> { /* Do nothing */ }
        }
    }
    list.sortedBy { file -> file.name }
    return list
}
private fun logDetails(files: List<ComposeMetricFile>) {
    if (files.isEmpty()) return
    println()
    println("${files.count()} metrics file(s) found :")
    val padStart = files.size.toString().length
    files.forEachIndexed { index, file ->
        val modIndex = (index + 1).toString().padStart(padStart)
        val module = file.module
        println("$modIndex. :${module.name} (${module.buildVariant})")
    }
}

internal const val METRICS_FILE_POSTFIX: String = "-composables.txt"
