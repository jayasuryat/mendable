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
package com.jayasuryat.mendable.parse

import com.jayasuryat.mendable.metricsfile.MetricsFile
import com.jayasuryat.mendable.parser.Parser
import com.jayasuryat.mendable.parser.model.ComposablesReport

internal class WarningsOnlyParser(
    private val backingParser: Parser,
) : Parser {

    override fun parse(
        files: List<MetricsFile>,
    ): ComposablesReport {

        val backingReport = backingParser.parse(files)

        val moduleReports: List<ComposablesReport.ModuleReport> = backingReport.moduleReports

        val filteredReports = moduleReports.filter { it.overview.skippablePercentage != 100 }

        return ComposablesReport(
            moduleReports = filteredReports,
            overview = backingReport.overview,
            totalModulesScanned = backingReport.moduleReports.size,
            totalModulesReported = filteredReports.size,
        )
    }
}
