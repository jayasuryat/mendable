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
package com.jayasuryat.mendable.parser

import com.jayasuryat.mendable.model.ComposablesReportFile
import com.jayasuryat.mendable.model.Module
import java.io.File

internal fun getReportFiles(
    path: String,
): List<ComposablesReportFile> {

    println()
    println("Reading files at path $path")

    val rootFile = File(path)

    rootFile.listFiles { file -> file.isFile && file.name.endsWith(METRICS_FILE_POSTFIX) }

    val metricFiles: List<File> = rootFile
        .listFiles { file -> file.isFile && file.name.endsWith(METRICS_FILE_POSTFIX) }
        ?.sortedBy { file -> file.name }
        ?: return emptyList()

    return metricFiles.map { file ->

        // Example file name : compose_release-composables.txt

        val name = file.name
        val nameIndex = name.indexOf('_')
        val moduleName = name.take(nameIndex)
        val buildVariant = name.subSequence(
            startIndex = nameIndex + 1,
            endIndex = name.length - METRICS_FILE_POSTFIX.length,
        ).toString()

        ComposablesReportFile(
            file = file,
            module = Module(
                name = moduleName,
                buildVariant = buildVariant,
            ),
        )
    }.also(::logDetails)
}

private fun logDetails(files: List<ComposablesReportFile>) {
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

private const val METRICS_FILE_POSTFIX: String = "-composables.txt"
