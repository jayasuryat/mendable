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
package com.jayasuryat.mendable.metricsfile

import dev.drewhamilton.poko.Poko
import java.io.File

/**
 * Represents a Compose compiler metrics file.
 *
 * This sealed interface defines the contract for Compose compiler metrics files. Each implementation of this interface
 * represents a specific type of Compose compiler metrics file. The purpose of creating subtype for each file is to
 * differentiate them at a type level.
 */
public sealed interface ComposeCompilerMetricsFile {

    /** The file which contains the Compose compiler metrics. */
    public val file: File

    /** The module to which this metrics file belongs to. */
    public val module: Module

    /**
     * Represents the report containing composable function signatures.
     * Generally files with name in `<module-name>-composables.txt` format.
     */
    @Poko
    public class ComposableSignaturesReportFile(
        override val file: File,
        override val module: Module,
    ) : ComposeCompilerMetricsFile {

        public companion object
    }

    /**
     * Represents the report containing information about stability of classes.
     * Generally files with name in `<module-name>-classes.txt` format.
     */
    @Poko
    public class ClassStabilityReportFile(
        override val file: File,
        override val module: Module,
    ) : ComposeCompilerMetricsFile {

        public companion object
    }

    /**
     * Represents the report containing high-level metrics specific to every composable function in a CSV format
     * Generally files with name in `<module-name>-composables.csv` format.
     */
    @Poko
    public class ComposableTabularReportFile(
        override val file: File,
        override val module: Module,
    ) : ComposeCompilerMetricsFile {

        public companion object
    }

    /**
     * Represents the metrics report for an entire module.
     * Generally files with name in `<module-name>-module.json` format.
     */
    @Poko
    public class ModuleMetricsFile(
        override val file: File,
        override val module: Module,
    ) : ComposeCompilerMetricsFile {

        public companion object
    }

    public companion object
}
