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
package com.jayasuryat.mendable.scanner

import com.jayasuryat.mendable.metricsfile.Module

/**
 * A factory to produce [Module] from the passed file name. `filename` here represents any generated
 * Compose-Compiler-Metrics' file name.
 */
public fun interface ModuleFactory {

    public fun parseModule(
        fileName: String,
    ): Module
}

/**
 * A default implementation of [ModuleFactory]. Parses [Module] using underscore and hyphens ('_' & '-') as delimiters.
 * For example: For "app_qaRelease-composables.txt", a [Module] with "app" as [Module.name] and "qaRelease" as
 * [Module.buildVariant] would be produced.
 *
 * Note : While this implementation should work for most of the cases, this will not generate expected outputs for files
 * whose original module's build variants' name have underscores in them.
 * For example: "feature_a_build_variant-composables.txt"; here expectation is "feature_a" should be the [Module.name]
 * and "build_variant" should be [Module.buildVariant]. But due to the nature of this implementation, "feature_a_build"
 * and "variant" would be [Module.name] and [Module.buildVariant] respectively for this case.
 */
public class DefaultModuleFactory : ModuleFactory {

    override fun parseModule(
        fileName: String,
    ): Module {
        val partialFileName = fileName.take(fileName.lastIndexOf('-'))
        val separatorIndex: Int = partialFileName.lastIndexOf('_')
        val moduleName: String = partialFileName.take(separatorIndex)
        val buildVariant: String = partialFileName.removePrefix(moduleName).drop(1)
        return Module(
            name = moduleName,
            buildVariant = buildVariant,
        )
    }
}
