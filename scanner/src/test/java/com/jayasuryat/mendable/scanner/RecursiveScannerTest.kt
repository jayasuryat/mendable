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
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.rules.TemporaryFolder

class RecursiveScannerTest {

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @BeforeEach
    fun setup() {
        temporaryFolder.create()
    }

    @Test
    fun `should read correct number of files with details`() {

        // region : Given
        val buildVariants = listOf(
            "release",
            "qaRelease",
            "standardRelease",
        ).sorted()

        val root = listOf(
            "discover",
            "trending",
            "showdetails",
        ).sorted()

        val rootVariants = root.createVariantsWith(variants = buildVariants)
        rootVariants.forEach { name ->
            temporaryFolder.newFile(name.composablesTxt())
            temporaryFolder.newFile(name.classesTxt())
            temporaryFolder.newFile(name.composablesCsv())
        }

        val nest1 = listOf(
            "account",
            "followed",
            "popular",
        ).sorted()

        temporaryFolder.newFolder("nest1")
        val nest1Variants = nest1.createVariantsWith(variants = buildVariants)
        nest1Variants.forEach { name ->
            temporaryFolder.newFile("nest1/" + name.composablesTxt())
            temporaryFolder.newFile("nest1/" + name.classesTxt())
            temporaryFolder.newFile("nest1/" + name.composablesCsv())
        }

        val nest2 = listOf(
            "watched",
            "search",
            "app",
        ).sorted()

        temporaryFolder.newFolder("moduleB/nest2")
        val nest2variants = nest2.createVariantsWith(variants = buildVariants)
        nest2variants.forEach { name ->
            temporaryFolder.newFile("moduleB/nest2/" + name.composablesTxt())
            temporaryFolder.newFile("moduleB/nest2/" + name.classesTxt())
            temporaryFolder.newFile("moduleB/nest2/" + name.composablesCsv())
        }

        val allFiles: List<String> = listOf(root, nest1, nest2).flatten()
        val allFilesWithVariants: List<String> = allFiles
            .createVariantsWith(variants = buildVariants)
            .sorted()
        // endregion

        // region: When
        val scannedFiles: List<MetricsFile> = scanForMetricsFilesRecursively(directory = temporaryFolder.root.path)
            .sortedBy { metricsFile -> "${metricsFile.module.name}_${metricsFile.module.buildVariant}" }
        // endregion

        // region : Then
        Assertions.assertEquals(allFilesWithVariants.size, scannedFiles.size)

        zipWithVariants(
            names = allFiles,
            variants = buildVariants,
            metrics = scannedFiles
        ).forEach { (name, variant, metrics) ->
            Assertions.assertEquals(name, metrics.module.name)
            Assertions.assertEquals(variant, metrics.module.buildVariant)
        }

        // Root files
        scannedFiles.filter { metrics -> metrics.file.parentFile.name == temporaryFolder.root.name }
            .apply { Assertions.assertEquals(rootVariants.size, this.size) }
            .forEach { metrics ->
                Assertions.assertEquals(temporaryFolder.root.name, metrics.file.parentFile.name)
            }

        scannedFiles.filter { metrics -> metrics.file.parentFile.name == "nest1" }
            .apply { Assertions.assertEquals(nest1Variants.size, this.size) }
            .forEach { metrics ->
                Assertions.assertEquals("nest1", metrics.file.parentFile.name)
            }

        scannedFiles.filter { metrics -> metrics.file.parentFile.name == "nest2" }
            .apply { Assertions.assertEquals(nest2variants.size, this.size) }
            .forEach { metrics ->
                Assertions.assertEquals("nest2", metrics.file.parentFile.name)
                Assertions.assertEquals("moduleB", metrics.file.parentFile.parentFile.name)
            }
        // endregion
    }
}
