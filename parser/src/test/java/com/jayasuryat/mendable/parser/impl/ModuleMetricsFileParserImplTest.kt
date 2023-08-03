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
package com.jayasuryat.mendable.parser.impl

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.ModuleMetricsFile
import com.jayasuryat.mendable.parser.ModuleMetricsFileParser
import com.jayasuryat.mendable.parser.util.getModuleMetricsFileFromResources
import io.kotest.assertions.throwables.shouldNotThrow
import org.junit.Test

class ModuleMetricsFileParserImplTest {

    private val parser: ModuleMetricsFileParser = ModuleMetricsFileParserImpl()

    @Test
    fun `ModuleMetricsFileParserImpl should not throw exception`() {
        // Read and get the report file from test resources folder
        val mapped: ModuleMetricsFile =
            getModuleMetricsFileFromResources("module_metrics/app_qaRelease-module.json")

        // Parsing the mapped values, this is the part being tested
        shouldNotThrow<Throwable> {
            parser.parse(mapped)
        }
    }
}
