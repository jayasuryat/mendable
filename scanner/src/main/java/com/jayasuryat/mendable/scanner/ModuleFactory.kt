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
import java.io.File

/**
 * A factory to produce [Module] from the passed file name. `filename` here represents any generated
 * Compose-Compiler-Metrics' file name.
 */
public fun interface ModuleFactory {

    public fun parseModule(
        file: File,
    ): Module
}

/**
 * Default implementation of [ModuleFactory].
 *
 * This implementation parses a [Module] from a file name using underscores ('_') and hyphens ('-')
 * as delimiters. It assumes a convention where:
 * - The file name is structured as "<module_name>_<build_variant>-<suffix>.<extension>".
 * - The [Module.name] is derived from the part of the file name before the last underscore ('_').
 * - The [Module.buildVariant] is derived from the part of the file name between the last underscore
 *   ('_') and the last hyphen ('-'). Build variant is considered optional, if build variant is
 *   missing in the file name, the resulting [Module] will have [Module.buildVariant] as `null`.
 *
 * ### Example
 * For a file named `app_qaRelease-composables.txt`:
 * - [Module.name] would be `"app"`
 * - [Module.buildVariant] would be `"qaRelease"`
 *
 * ### Limitations
 * This implementation may produce unexpected results for file names where the build variant or the
 * module name contains hyphens or underscores.
 */
public class DefaultModuleFactory : ModuleFactory {

    override fun parseModule(
        file: File,
    ): Module {
        val fileName = file.name
        val partialFileName = fileName.take(fileName.lastIndexOf('-'))
        val separatorIndex: Int = partialFileName.lastIndexOf('_')

        val hasBuildVariant = separatorIndex != -1
        val moduleName: String
        val buildVariant: String?
        if (hasBuildVariant) {
            moduleName = partialFileName.take(separatorIndex)
            buildVariant = partialFileName.removePrefix(moduleName).drop(1)
        } else {
            moduleName = partialFileName
            buildVariant = null
        }

        return Module(
            name = moduleName,
            buildVariant = buildVariant,
        )
    }
}
