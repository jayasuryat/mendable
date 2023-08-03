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
package com.jayasuryat.mendable.parser

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.parser.impl.ClassStabilityReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ComposableSignaturesReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ComposableTabularReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ModuleMetricsFileParserImpl
import com.jayasuryat.mendable.parser.model.ClassStabilityReport
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport
import com.jayasuryat.mendable.parser.model.ComposableTabularReport
import com.jayasuryat.mendable.parser.model.ModuleMetrics

public fun getComposeCompilerMetricsParser(
    composableSignaturesReportFileParser: ComposableSignaturesReportFileParser = ComposableSignaturesReportFileParserImpl(),
    classStabilityReportFileParser: ClassStabilityReportFileParser = ClassStabilityReportFileParserImpl(),
    composableTabularReportFileParser: ComposableTabularReportFileParser = ComposableTabularReportFileParserImpl(),
    moduleMetricsFileParser: ModuleMetricsFileParser = ModuleMetricsFileParserImpl(),
): ComposeCompilerMetricsParser {

    return ComposeCompilerMetricsParser(
        composableSignaturesReportFileParser = composableSignaturesReportFileParser,
        classStabilityReportFileParser = classStabilityReportFileParser,
        composableTabularReportFileParser = composableTabularReportFileParser,
        moduleMetricsFileParser = moduleMetricsFileParser,
    )
}

public fun getComposableSignaturesReportFileParser(): ComposableSignaturesReportFileParser {
    return ComposableSignaturesReportFileParserImpl()
}

public fun getClassStabilityReportFileParser(): ClassStabilityReportFileParser {
    return ClassStabilityReportFileParserImpl()
}

public fun getComposableTabularReportFileParser(): ComposableTabularReportFileParser {
    return ComposableTabularReportFileParserImpl()
}

public fun getModuleMetricsFileParser(): ModuleMetricsFileParser {
    return ModuleMetricsFileParserImpl()
}

public fun ComposableSignaturesReportFile.parse(): ComposableSignaturesReport {
    val parser = getComposableSignaturesReportFileParser()
    return parser.parse(this)
}

public fun ClassStabilityReportFile.parse(): ClassStabilityReport {
    val parser = getClassStabilityReportFileParser()
    return parser.parse(this)
}

public fun ComposableTabularReportFile.parse(): ComposableTabularReport {
    val parser = getComposableTabularReportFileParser()
    return parser.parse(this)
}

public fun ModuleMetricsFile.parse(): ModuleMetrics {
    val parser = getModuleMetricsFileParser()
    return parser.parse(this)
}
