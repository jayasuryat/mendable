package com.jayasuryat.mendable.parser

import com.jayasuryat.mendable.metricsfile.ComposeCompilerMetricsFile.*
import com.jayasuryat.mendable.parser.util.getClassStabilityReportFileFromResources
import com.jayasuryat.mendable.parser.util.getComposableSignaturesReportFileFromResources
import com.jayasuryat.mendable.parser.util.getComposableTabularReportFileFromResources
import com.jayasuryat.mendable.parser.util.getModuleMetricsFileFromResources
import io.kotest.assertions.throwables.shouldNotThrow
import org.junit.Test

class ComposeCompilerMetricsFileParserTest {

    private val parser: ComposeCompilerMetricsFileParser = getComposeCompilerMetricsFileParser()

    @Test
    fun `ComposeCompilerMetricsFileParser should be able to parse all type of report files`() {

        val composableSignaturesReportFile: ComposableSignaturesReportFile =
            getComposableSignaturesReportFileFromResources("composable_signature/app_release-composables.txt")

        val classStabilityReportFileFrom: ClassStabilityReportFile =
            getClassStabilityReportFileFromResources("class_stability/app_qaRelease-classes.txt")

        val composableTabularReportFile: ComposableTabularReportFile =
            getComposableTabularReportFileFromResources("composable_tabular/bulky_release-composables.csv")

        val moduleMetricsFile: ModuleMetricsFile =
            getModuleMetricsFileFromResources("module_metrics/app_qaRelease-module.json")

        shouldNotThrow<Throwable> {
            parser.parse(composableSignaturesReportFile)
            parser.parse(classStabilityReportFileFrom)
            parser.parse(composableTabularReportFile)
            parser.parse(moduleMetricsFile)
        }
    }
}