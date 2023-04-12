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
package com.jayasuryat.mendable

import com.jayasuryat.mendable.filereader.METRICS_FILE_POSTFIX
import com.jayasuryat.mendable.filereader.getReportFiles
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder

class GetReportFilesTest {

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @BeforeEach
    fun setup() {
        temporaryFolder.create()
    }

    @Test
    fun `should read correct number of files`() {

        val moduleNames = listOf(
            "app",
            "episodedetails",
            "location-details",
            "character-detail-report",
        ).sorted()

        val buildVariants = listOf(
            "release",
            "qaRelease",
            "demoRelease",
            "prodRelease",
            "client-A",
            "client-B",
        ).sorted()

        val fileNames: List<String> = moduleNames.map { name ->
            buildVariants.map { variant ->
                "${name}_${variant}$METRICS_FILE_POSTFIX"
            }
        }.flatten()

        fileNames.forEach { name -> temporaryFolder.newFile(name) }

        val files = getReportFiles(path = temporaryFolder.root.path)
            .sortedBy { it.module.displayName }

        Assertions.assertEquals(fileNames.size, files.size)

        val filesIterator = files.iterator()

        moduleNames.forEach { name ->
            buildVariants.forEach { variant ->
                val module = filesIterator.next().module
                Assertions.assertEquals(module.name, name)
                Assertions.assertEquals(module.buildVariant, variant)
            }
        }
    }

    @Test
    fun `should read child directories`() {

        val moduleNames = listOf(
            "app",
            "episodedetails",
        ).sorted()

        val buildVariants = listOf(
            "release",
            "qaRelease",
        ).sorted()

        val testParentFolderName = "testParentFolder"
        val fileNames: List<String> = moduleNames.map { name ->
            buildVariants.map { variant ->
                "$testParentFolderName/${name}_${variant}$METRICS_FILE_POSTFIX"
            }
        }.flatten()

        temporaryFolder.newFolder(testParentFolderName)

        fileNames.forEach { name -> temporaryFolder.newFile(name) }

        val files = getReportFiles(path = temporaryFolder.root.path)
            .sortedBy { it.module.displayName }

        Assertions.assertEquals(fileNames.size, files.size)

        val filesIterator = files.iterator()

        moduleNames.forEach { name ->
            buildVariants.forEach { variant ->
                val module = filesIterator.next().module
                Assertions.assertEquals(module.name, name)
                Assertions.assertEquals(module.buildVariant, variant)
            }
        }
    }
}
