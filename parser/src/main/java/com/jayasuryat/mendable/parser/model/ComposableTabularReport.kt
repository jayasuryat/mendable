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
package com.jayasuryat.mendable.parser.model

import com.jayasuryat.mendable.metricsfile.Module
import dev.drewhamilton.poko.Poko

/**
 * Represents a tabular report containing information about composable functions within a module. This report includes
 * details about each composable function's characteristics and statistics.
 *
 *  Generally represents files with name in `<module-name>-composables.csv` format.
 *
 * @property module The module to which this composable tabular report pertains.
 * @property composables The list of tabulated composable details in this report.
 */
@Poko
public class ComposableTabularReport(
    public override val module: Module,
    public val composables: List<ComposableDetails>,
) : ComposeCompilerMetrics {

    /**
     * Represents tabulated information about a composable function.
     *
     * @property name The name of the composable function.
     * @property packageName The name of the package containing the composable function.
     * @property skippable Indicates whether the composable function is skippable or not.
     * @property restartable Indicates whether the composable function is restartable or not.
     * @property readonly Indicates whether the composable function is readonly or not.
     * @property inline Indicates whether the composable function is inline or not.
     * @property isLambda Indicates whether the composable function is a lambda or not.
     * @property hasDefaults Indicates whether the composable function has defaults or not.
     * @property defaultsGroup
     * @property groups
     * @property calls The number of times the composable function is called.
     */
    @Poko
    public class ComposableDetails(
        public val name: String,
        public val packageName: String,
        public val skippable: Boolean,
        public val restartable: Boolean,
        public val readonly: Boolean,
        public val inline: Boolean,
        public val isLambda: Boolean,
        public val hasDefaults: Boolean,
        public val defaultsGroup: Int,
        public val groups: Int,
        public val calls: Int,
    ) {

        public companion object
    }

    public companion object
}
