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
package com.jayasuryat.mendable.export.json

import com.jayasuryat.mendable.getTestMetricsFilesFromResources
import com.jayasuryat.mendable.parse.WarningsOnlyParser
import com.jayasuryat.mendable.parser.Parser
import com.jayasuryat.mendable.parser.create
import com.jayasuryat.mendable.readFileAsTextFromResources
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

internal class JsonExportUtilTest {

    @Test
    fun `should Json-ify report correctly`() {

        val content = WarningsOnlyParser(Parser.create())
            .parse(getTestMetricsFilesFromResources())
            .jsonExportContent()

        val expected = readFileAsTextFromResources("warning-report-expected-json.json")

        JSONAssert.assertEquals(expected, content, JSONCompareMode.NON_EXTENSIBLE)
    }
}
