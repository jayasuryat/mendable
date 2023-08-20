# Using `Mendable` as a `Library`

Individual subcomponents of `Mendable` are published as `Maven artifacts` on `MavenCentral`. These are *good old Java
libraries*, which you can consume in any `tooling` or `library` project you might be building around `Jetpack Compose`
`Compiler` `Metrics`.

## Artifacts

The whole functionality of `Mendable` has been split into 3 major modules, based on your requirements, you can pick
and choose what you want.

1. `:scanner` : Scan a directory to find all (or some) of the `Compose Compiler Metrics` files.
2. `:parser` : Parse raw `Compose Compiler Metrics` files into operable `Kotlin` `objects`.
3. `:mendable` : Is a high-level library which scans, parses, and renders these reports as `HTML`/`JSON` reports by
using `:scanner` & `:parser` under the hood.

## Setup

Add the following dependencies to your project

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.jayasuryat.mendable:scanner:0.6.1")
    implementation("com.jayasuryat.mendable:parser:0.6.1")
    implementation("com.jayasuryat.mendable:mendable:0.6.1")
}
```

---

## Usages

### `:scanner`

All the functionality of the `scanner` module is exposed as `top-level` `functions`. There are individual functions which
scan for the individual report type, and a consolidating function which scans for all types of report files and returns
them.

```Kotlin hl_lines="9 10 11 12 16 20 24 28"
// import com.jayasuryat.mendable.scanner.scanForComposableSignaturesReportFiles
// import com.jayasuryat.mendable.scanner.scanForClassStabilityReportFiles
// import com.jayasuryat.mendable.scanner.scanForComposableTabularReportFiles
// import com.jayasuryat.mendable.scanner.scanForModuleMetricsFiles
// import com.jayasuryat.mendable.scanner.scanForAllComposeCompilerMetricsFiles

// <module-name>-composables.txt files
val composables: List<ComposableSignaturesReportFile> =
    scanForComposableSignaturesReportFiles(
        directory = directory,
        scanRecursively = true,
    )

// <module-name>-classes.txt files
val classes: List<ClassStabilityReportFile> =
    scanForClassStabilityReportFiles(directory)

// <module-name>-composables.csv files
val tabularReport: List<ComposableTabularReportFile> =
    scanForComposableTabularReportFiles(directory)

// <module-name>-module.json files
val moduleMetrics: List<ModuleMetricsFile> =
    scanForModuleMetricsFiles(directory)

// Combines all the 4 types above
val allReports: List<ComposableSignaturesReportFile> =
    scanForAllComposeCompilerMetricsFiles(directory)
```

---

### `:parser`

`:parser` exposes `*.parse()` `extension method` on all the types of report-files, which returns the
`parsed` report.

!!! info ""

    Alternatively `:parser` also exposes individual `Parsers` which can `parse` all the different type of reports. This
    could come in handy if you want to reuse the `parser` `instace` while parsing mulitple files.

=== "Extension"

    ```kotlin hl_lines="4 7 10 13 16"
    // import com.jayasuryat.mendable.parser.parse

    val composables: ComposableSignaturesReportFile = TODO()
    val parsedComposables: ComposableSignaturesReport = composables.parse()

    val classes: ClassStabilityReportFile = TODO()
    val parsedClasses: ClassStabilityReport = classes.parse()

    val tabularReport: ComposableTabularReportFile = TODO()
    val parsedTabularReport: ComposableTabularReport = tabularReport.parse()

    val moduleMetrics: ModuleMetricsFile = TODO()
    val parsedModuleMetrics: ModuleMetrics = moduleMetrics.parse()

    val someMetricsFile: ComposeCompilerMetricsFile = TODO()
    val parsedMetrics: ComposeCompilerMetrics = someMetricsFile.parse()
    ```

=== "Parser instance"

    ```kotlin hl_lines="8 12 16 20 24"
    // com.jayasuryat.mendable.parser.getComposableSignaturesReportFileParser
    // com.jayasuryat.mendable.parser.getClassStabilityReportFileParser
    // com.jayasuryat.mendable.parser.getComposableTabularReportFileParser
    // com.jayasuryat.mendable.parser.getModuleMetricsFileParser
    // com.jayasuryat.mendable.parser.getComposeCompilerMetricsParser

    val composables: ComposableSignaturesReportFile = TODO()
    val composablesParser: ComposableSignaturesReportFileParser = getComposableSignaturesReportFileParser()
    val parsedComposables: ComposableSignaturesReport = composablesParser.parse(composables)

    val classes: ClassStabilityReportFile = TODO()
    val classesParser: ClassStabilityReportFileParser = getClassStabilityReportFileParser()
    val parsedClasses: ClassStabilityReport = classesParser.parse(classes)

    val tabularReport: ComposableTabularReportFile = TODO()
    val tabularReportParser: ComposableTabularReportFileParser = getComposableTabularReportFileParser()
    val parsedTabularReport: ComposableTabularReport = tabularReportParser.parse(tabularReport)

    val moduleMetrics: ModuleMetricsFile = TODO()
    val moduleMetricsParser: ModuleMetricsFileParser = getModuleMetricsFileParser()
    val parsedModuleMetrics: ModuleMetrics = moduleMetricsParser.parse(moduleMetrics)

    val someReport: ComposeCompilerMetricsFile = TODO()
    val allParser: ComposeCompilerMetricsParser = getComposeCompilerMetricsParser()
    val report1 = allParser.parser(someReport)
    val report2 = allParser.parser(composables)
    val report3 = allParser.parser(classes)
    val report4 = allParser.parser(tabularReport)
    val report5 = allParser.parser(moduleMetrics)
    ```

---

### `:mendable`

Mendable exposes a single report generator class which takes in a request and generates a `HTML` or `Json` report (based
on the request) and saves it to file system.

```kotlin hl_lines="17 22 23 24"
// import com.jayasuryat.mendable.MendableReportGenerator
// import com.jayasuryat.mendable.MendableReportGeneratorRequest

val request = MendableReportGeneratorRequest(
    scanPath = "your input path",
    scanRecursively = false,
    outputPath = "your output path",
    outputFileName = "reportName",
    exportType = ExportType.HTML, // Can also be ExportType.JSON
    includeModules = IncludeModules.ALL, // Can also be IncludeModules.WITH_WARNINGS
)

val generator = MendableReportGenerator()

// This is a suspending function
val result = MendableReportGenerator.Progress.Result =
    generator.generate(request = request)

// Also, there is an overload which notifies of the progress
// This is also suspending function
val result: MendableReportGenerator.Progress.Result =
    generator.generate(request = request) { progress ->
        println(progress)
    }
```
