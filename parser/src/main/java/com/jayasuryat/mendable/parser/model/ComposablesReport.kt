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
package com.jayasuryat.mendable.parser.model

import com.jayasuryat.mendable.metricsfile.Module

public class ComposablesReport(
    public val moduleReports: List<ModuleReport>,
    public val overview: Overview,
    public val totalModulesScanned: Int,
    public val totalModulesReported: Int,
) {

    public val totalModulesFiltered: Int = totalModulesScanned - totalModulesReported

    public class ModuleReport(
        public val module: Module,
        public val overview: Overview,
        public val composables: List<ComposableDetails>,
    ) {

        public class ComposableDetails(
            public val functionName: String,
            public val isRestartable: Boolean,
            public val isSkippable: Boolean,
            public val isInline: Boolean,
            public val params: List<Parameter>,
        ) {

            public class Parameter(
                public val condition: Condition,
                public val name: String,
                public val type: String,
            ) {

                public enum class Condition {
                    STABLE,
                    UNSTABLE,
                    UNUSED,
                    UNKNOWN;

                    public companion object
                }

                public companion object
            }

            public companion object
        }

        public companion object
    }

    public class Overview(
        public val totalComposables: Int,
        public val restartableComposables: Int,
        public val skippableComposables: Int,
        public val skippablePercentage: Int,
    ) {

        public companion object
    }

    public companion object
}
