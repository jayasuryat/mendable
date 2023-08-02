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

public class ComposableTabularReport(
    public override val module: Module,
    public val composables: List<ComposableDetails>,
) : ComposeCompilerMetrics {

    public class ComposableDetails(
        public val packageName: String,
        public val name: String,
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
