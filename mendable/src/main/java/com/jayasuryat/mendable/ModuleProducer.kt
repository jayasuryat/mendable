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
package com.jayasuryat.mendable

import com.jayasuryat.mendable.metricsfile.Module
import com.jayasuryat.mendable.scanner.DefaultModuleFactory
import com.jayasuryat.mendable.scanner.ModuleFactory
import java.io.File

/**
 * A factory to produce [Module] from the passed Compose-Compiler-Metrics' file.
 *
 * Note : This is a surrogate to [ModuleFactory], as using [ModuleFactory] in this module's public API
 * will force the consumers to add dependency on :parser module as well, which is not really necessary.
 *
 * @see [ModuleFactory]
 */
public fun interface ModuleProducer {

    public fun parseModule(
        file: File,
    ): Module

    public companion object {

        public val Default: ModuleProducer
            get() {
                return object : ModuleProducer {
                    private val backingFactory: ModuleFactory = DefaultModuleFactory()
                    override fun parseModule(file: File): Module = backingFactory.parseModule(file)
                }
            }
    }
}

internal fun ModuleProducer.toModuleFactory(): ModuleFactory {
    return ModuleFactory { file ->
        this.parseModule(file)
    }
}
