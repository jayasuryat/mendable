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
import com.jayasuryat.mendable.parser.model.ClassStabilityReport
import com.jayasuryat.mendable.parser.model.ComposableSignaturesReport
import com.jayasuryat.mendable.parser.model.ComposableTabularReport
import com.jayasuryat.mendable.parser.model.ModuleMetrics

/**
 * A parser contract for parsing [ComposableSignaturesReportFile] into [ComposableSignaturesReport].
 */
public interface ComposableSignaturesReportFileParser {
    public fun parse(
        file: ComposableSignaturesReportFile,
    ): ComposableSignaturesReport
}

/**
 * A parser contract for parsing [ClassStabilityReportFile] into [ClassStabilityReport].
 */
public interface ClassStabilityReportFileParser {
    public fun parse(
        file: ClassStabilityReportFile,
    ): ClassStabilityReport
}

/**
 * A parser contract for parsing [ComposableTabularReportFile] into [ComposableTabularReport].
 */
public interface ComposableTabularReportFileParser {
    public fun parse(
        file: ComposableTabularReportFile,
    ): ComposableTabularReport
}

/**
 * A parser contract for parsing [ModuleMetricsFileParser] into [ModuleMetricsFile].
 */
public interface ModuleMetricsFileParser {
    public fun parse(
        file: ModuleMetricsFile,
    ): ModuleMetrics
}

/**
 * A parser that combines all known metrics-files-parsers into a single class.
 *
 * This parser delegates the parsing of specific report file types to its constituent parsers, which are provided during
 * construction.
 */
public class ComposeCompilerMetricsParser internal constructor(
    composableSignaturesReportFileParser: ComposableSignaturesReportFileParser,
    classStabilityReportFileParser: ClassStabilityReportFileParser,
    composableTabularReportFileParser: ComposableTabularReportFileParser,
    moduleMetricsFileParser: ModuleMetricsFileParser,
) : ComposableSignaturesReportFileParser by composableSignaturesReportFileParser,
    ClassStabilityReportFileParser by classStabilityReportFileParser,
    ComposableTabularReportFileParser by composableTabularReportFileParser,
    ModuleMetricsFileParser by moduleMetricsFileParser {

    public companion object
}
