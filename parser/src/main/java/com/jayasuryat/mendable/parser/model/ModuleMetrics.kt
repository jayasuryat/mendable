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

public class ModuleMetrics(
    override val module: Module,
    public val details: Details,
) : ComposeCompilerMetrics {

    public class Details(
        public val skippableComposables: Int,
        public val restartableComposables: Int,
        public val readonlyComposables: Int,
        public val totalComposables: Int,
        public val restartGroups: Int,
        public val totalGroups: Int,
        public val staticArguments: Int,
        public val certainArguments: Int,
        public val knownStableArguments: Int,
        public val knownUnstableArguments: Int,
        public val unknownStableArguments: Int,
        public val totalArguments: Int,
        public val markedStableClasses: Int,
        public val inferredStableClasses: Int,
        public val inferredUnstableClasses: Int,
        public val inferredUncertainClasses: Int,
        public val effectivelyStableClasses: Int,
        public val totalClasses: Int,
        public val memoizedLambdas: Int,
        public val singletonLambdas: Int,
        public val singletonComposableLambdas: Int,
        public val composableLambdas: Int,
        public val totalLambdas: Int,
    ) {

        public companion object
    }

    public companion object
}
