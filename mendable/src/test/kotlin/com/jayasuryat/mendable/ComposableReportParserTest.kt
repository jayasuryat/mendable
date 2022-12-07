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

import com.google.gson.GsonBuilder
import com.jayasuryat.mendable.model.ComposablesReport
import com.jayasuryat.mendable.model.ComposablesReportFile
import com.jayasuryat.mendable.parser.ComposableReportParser
import com.jayasuryat.mendable.parser.getReportFiles
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.math.roundToInt

class ComposableReportParserTest {

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @BeforeEach
    fun setup() {
        temporaryFolder.create()
    }

    @Test
    fun `ComposableReportParser should parse all composables correctly`() {

        // Dumping composables.txt into temporaryFolder
        val file: File = temporaryFolder.newFile("app_release-composables.txt")
        val textToWrite = readFileAsTextFromResources(fileName = "composables.txt")
        require(!textToWrite.isNullOrBlank())
        file.writeText(text = textToWrite)

        // Mapping
        val mapped: List<ComposablesReportFile> = getReportFiles(path = temporaryFolder.root.absolutePath)
        require(mapped.size == 1)

        // Parsing the mapped values, this is the part being tested
        val report: ComposablesReport = ComposableReportParser().parse(mapped)

        val overview = report.overview
        Assertions.assertEquals(overview.totalComposables, 16)
        Assertions.assertEquals(overview.restartableComposables, 12)
        Assertions.assertEquals(overview.skippableComposables, 11)
        val percentage = ((11 * 100f) / 12).roundToInt()
        Assertions.assertEquals(overview.skippablePercentage, percentage)

        Assertions.assertEquals(report.totalModules, 1)

        val moduleOverview = report.moduleReports.first().overview
        Assertions.assertEquals(overview, moduleOverview)

        val expected = readFileAsTextFromResources(fileName = "composables.json")
        require(!expected.isNullOrBlank())

        val actual = GsonBuilder().disableHtmlEscaping().create().toJson(report)
        Assertions.assertEquals(expected, actual)
    }
}
