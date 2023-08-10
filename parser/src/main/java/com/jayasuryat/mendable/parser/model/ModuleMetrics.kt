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
 * This class provides detailed metrics information about a module's composable functions and classes.
 *
 * Generally represents files with name in `<module-name>-module.json` format.
 *
 * @property module The module to which these metrics pertain.
 * @property details The detailed metrics for the module.
 */
@Poko
public class ModuleMetrics(
    override val module: Module,
    public val details: Details,
) : ComposeCompilerMetrics {

    /**
     * Represents detailed metrics information for a module.
     *
     * @property skippableComposables The number of skippable composable functions.
     * @property restartableComposables The number of restartable composable functions.
     * @property readonlyComposables The number of readonly composable functions.
     * @property totalComposables The total number of composable functions.
     * @property restartGroups The number of restart groups in the module.
     * @property totalGroups The total number of groups in the module.
     * @property staticArguments The number of static arguments in the module.
     * @property certainArguments The number of certain arguments in the module.
     * @property knownStableArguments The number of known stable arguments in the module.
     * @property knownUnstableArguments The number of known unstable arguments in the module.
     * @property unknownStableArguments The number of unknown stable arguments in the module.
     * @property totalArguments The total number of arguments in the module.
     * @property markedStableClasses The number of classes marked as stable in the module.
     * @property inferredStableClasses The number of classes inferred as stable in the module.
     * @property inferredUnstableClasses The number of classes inferred as unstable in the module.
     * @property inferredUncertainClasses The number of classes inferred as uncertain in the module.
     * @property effectivelyStableClasses The number of effectively stable classes in the module.
     * @property totalClasses The total number of classes in the module.
     * @property memoizedLambdas The number of memoized lambdas in the module.
     * @property singletonLambdas The number of singleton lambdas in the module.
     * @property singletonComposableLambdas The number of singleton composable lambdas in the module.
     * @property composableLambdas The number of composable lambdas in the module.
     * @property totalLambdas The total number of lambdas in the module.
     */
    @Poko
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
