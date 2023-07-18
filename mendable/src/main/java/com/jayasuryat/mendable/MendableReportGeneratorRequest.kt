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

public class MendableReportGeneratorRequest(
    public val scanPath: String,
    public val outputPath: String,
    public val scanRecursively: Boolean = false,
    public val outputFileName: String = "report",
    public val exportType: ExportType = ExportType.HTML,
    public val includeModules: IncludeModules = IncludeModules.ALL,
) {

    public enum class ExportType {

        HTML,
        JSON,
        ;

        public companion object {

            public fun find(type: String): ExportType = ExportType.values()
                .firstOrNull { entry -> entry.name.equals(type, ignoreCase = true) }
                ?: error(
                    "$type does not match with any entries of enum `${ExportType::class.java.canonicalName}`." +
                        " Valid choices are ${ExportType.values().map { it.name }}"
                )
        }
    }

    public enum class IncludeModules {

        ALL,
        WITH_WARNINGS,
        ;

        public companion object {

            public fun find(type: String): IncludeModules = IncludeModules.values()
                .firstOrNull { entry -> entry.name.equals(type, ignoreCase = true) }
                ?: error(
                    "$type does not match with any entries of enum `${IncludeModules::class.java.canonicalName}`." +
                        " Valid choices are ${IncludeModules.values().map { it.name }}"
                )
        }
    }
}
