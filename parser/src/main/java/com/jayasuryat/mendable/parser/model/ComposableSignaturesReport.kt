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

/**
 * Represents a report containing information about composable functions within a module. This report includes details
 * about each composable function's characteristics and parameters.
 *
 * Generally represents files with name in `<module-name>-classes.txt` format.
 *
 * @property module The module to which this composable signatures report pertains.
 * @property composables The list of composables details in this report.
 */
public class ComposableSignaturesReport(
    public override val module: Module,
    public val composables: List<ComposableDetails>,
) : ComposeCompilerMetrics {

    /**
     * Represents detailed information about a composable function.
     *
     * @property functionName The name of the composable function.
     * @property isRestartable Indicates whether the composable function is restartable.
     * @property isSkippable Indicates whether the composable function is skippable.
     * @property isInline Indicates whether the composable function is inline.
     * @property params The list of parameters for the composable function.
     */
    public class ComposableDetails(
        public val functionName: String,
        public val isRestartable: Boolean,
        public val isSkippable: Boolean,
        public val isInline: Boolean,
        public val params: List<Parameter>,
    ) {

        /**
         * Represents a parameter of a composable function, including its condition and details.
         *
         * @property name The name of the parameter.
         * @property condition The condition of the parameter.
         * @property type The type of the parameter.
         * @property defaultValue The default value of the parameter, if applicable.
         */
        public class Parameter(
            public val name: String,
            public val condition: Condition,
            public val type: String,
            public val defaultValue: String?,
        ) {

            /**
             * Represents the condition of a parameter.
             */
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
