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

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile
import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.parser.impl.ClassStabilityReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ComposableSignaturesReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ComposableTabularReportFileParserImpl
import com.jayasuryat.mendable.parser.impl.ModuleMetricsFileParserImpl
import com.jayasuryat.mendable.parser.model.*

// region : Parser factories
/**
 * Factory function for creating an instance of [ComposeCompilerMetricsFileParser] using default or custom parsers for
 * each Compose compiler metrics file.
 *
 * @param composableSignaturesReportFileParser to parse [ComposableSignaturesReportFile].
 * @param classStabilityReportFileParser to parse [ClassStabilityReportFile].
 * @param composableTabularReportFileParser to parse [ComposableTabularReportFile].
 * @param moduleMetricsFileParser to parse [ModuleMetricsFile].
 * @return A composed instance of all the parsers, [ComposeCompilerMetricsFileParser].
 */
public fun getComposeCompilerMetricsFileParser(
    composableSignaturesReportFileParser: ComposableSignaturesReportFileParser = ComposableSignaturesReportFileParserImpl(),
    classStabilityReportFileParser: ClassStabilityReportFileParser = ClassStabilityReportFileParserImpl(),
    composableTabularReportFileParser: ComposableTabularReportFileParser = ComposableTabularReportFileParserImpl(),
    moduleMetricsFileParser: ModuleMetricsFileParser = ModuleMetricsFileParserImpl(),
): ComposeCompilerMetricsFileParser {

    return ComposeCompilerMetricsFileParser(
        composableSignaturesReportFileParser = composableSignaturesReportFileParser,
        classStabilityReportFileParser = classStabilityReportFileParser,
        composableTabularReportFileParser = composableTabularReportFileParser,
        moduleMetricsFileParser = moduleMetricsFileParser,
    )
}

/**
 * A factory function that creates and returns an instance of the default implementation of
 * [ComposableSignaturesReportFileParser].
 */
public fun getComposableSignaturesReportFileParser(): ComposableSignaturesReportFileParser {
    return ComposableSignaturesReportFileParserImpl()
}

/**
 * A factory function that creates and returns an instance of the default implementation of
 * [ClassStabilityReportFileParser].
 */
public fun getClassStabilityReportFileParser(): ClassStabilityReportFileParser {
    return ClassStabilityReportFileParserImpl()
}

/**
 * A factory function that creates and returns an instance of the default implementation of
 * [ComposableTabularReportFileParser].
 */
public fun getComposableTabularReportFileParser(): ComposableTabularReportFileParser {
    return ComposableTabularReportFileParserImpl()
}

/**
 * A factory function that creates and returns an instance of the default implementation of [ModuleMetricsFileParser].
 */
public fun getModuleMetricsFileParser(): ModuleMetricsFileParser {
    return ModuleMetricsFileParserImpl()
}
// endregion

// region : File parsers
/**
 * Extension function for parsing a [ComposableSignaturesReportFile] and generating [ComposableSignaturesReport].
 *
 * This extension function uses the default [ComposableSignaturesReportFileParser] implementation to parse the given
 * [ComposableSignaturesReportFile] and return a corresponding [ComposableSignaturesReport].
 *
 * @receiver The [ComposableSignaturesReportFile] to be parsed.
 * @return A [ComposableSignaturesReport] generated from the provided [ComposableSignaturesReportFile].
 */
public fun ComposableSignaturesReportFile.parse(): ComposableSignaturesReport {
    val parser = getComposableSignaturesReportFileParser()
    return parser.parse(this)
}

/**
 * Extension function for parsing a [ClassStabilityReportFile] and generating [ClassStabilityReport].
 *
 * This extension function uses the default [ClassStabilityReportFileParser] implementation to parse the given
 * [ClassStabilityReportFile] and return a corresponding [ClassStabilityReport].
 *
 * @receiver The [ClassStabilityReportFile] to be parsed.
 * @return A [ClassStabilityReport] generated from the provided [ClassStabilityReportFile].
 */
public fun ClassStabilityReportFile.parse(): ClassStabilityReport {
    val parser = getClassStabilityReportFileParser()
    return parser.parse(this)
}

/**
 * Extension function for parsing a [ComposableTabularReportFile] and generating [ComposableTabularReport].
 *
 * This extension function uses the default [ComposableTabularReportFileParser] implementation to parse the given
 * [ComposableTabularReportFile] and return a corresponding [ComposableTabularReport].
 *
 * @receiver The [ComposableTabularReportFile] to be parsed.
 * @return A [ComposableTabularReport] generated from the provided [ComposableTabularReportFile].
 */
public fun ComposableTabularReportFile.parse(): ComposableTabularReport {
    val parser = getComposableTabularReportFileParser()
    return parser.parse(this)
}

/**
 * Extension function for parsing a [ModuleMetricsFile] and generating [ModuleMetrics].
 *
 * This extension function uses the default [ModuleMetricsFileParser] implementation to parse the given
 * [ModuleMetricsFile] and return a corresponding [ModuleMetrics].
 *
 * @receiver The [ModuleMetricsFile] to be parsed.
 * @return A [ModuleMetrics] generated from the provided [ModuleMetricsFile].
 */
public fun ModuleMetricsFile.parse(): ModuleMetrics {
    val parser = getModuleMetricsFileParser()
    return parser.parse(this)
}

/**
 * Extension function for parsing a [ComposeCompilerMetricsFile] and generating [ComposeCompilerMetrics].
 *
 * This extension function uses [ComposeCompilerMetricsFileParser] to parse the given
 * [ComposeCompilerMetricsFile] and return a corresponding [ComposeCompilerMetrics].
 *
 * @receiver The [ComposeCompilerMetricsFile] to be parsed.
 * @return A [ComposeCompilerMetrics] generated from the provided [ComposeCompilerMetricsFile].
 */
public fun ComposeCompilerMetricsFile.parse(): ComposeCompilerMetrics {
    val parser = getComposeCompilerMetricsFileParser()
    return parser.parse(this)
}

// endregion
