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

import dev.drewhamilton.poko.Poko

/**
 * Represents a request for generating a mendable report.
 *
 * This class encapsulates the parameters required for generating a mendable report.
 *
 * @property scanPath The path to the directory where metrics files will be scanned.
 * @property outputPath The output path where the generated report will be saved.
 * @property scanRecursively Whether to scan for metrics files recursively within the directory.
 * @property outputFileName The name of the generated report file.
 * @property exportType The type of export for the report (HTML or JSON).
 * @property includeModules The inclusion criteria for modules in the report (ALL or WITH_WARNINGS).
 */
@Poko
public class MendableReportGeneratorRequest(
    public val scanPath: String,
    public val outputPath: String,
    public val scanRecursively: Boolean,
    public val outputFileName: String,
    public val exportType: ExportType,
    public val includeModules: IncludeModules,
) {

    /** Represents the type of export for the mendable report. */
    public enum class ExportType {

        HTML,
        JSON,
        ;

        public companion object {

            /**
             * Finds [ExportType] based on the provided type string.
             *
             * @param type The type string to match against enum entries.
             * @return The matching [ExportType].
             * @throws IllegalArgumentException if the provided type string doesn't match any enum entry.
             */
            public fun find(type: String): ExportType = entries
                .firstOrNull { entry -> entry.name.equals(type, ignoreCase = true) }
                ?: error(
                    "$type does not match with any entries of enum `${ExportType::class.java.canonicalName}`." +
                        " Valid choices are ${entries.map { it.name }}"
                )
        }
    }

    /** Represents the inclusion criteria for modules in the mendable report. */
    public enum class IncludeModules {

        ALL,
        WITH_WARNINGS,
        ;

        public companion object {

            /**
             * Finds an [IncludeModules] based on the provided type string.
             *
             * @param type The type string to match against enum entries.
             * @return The matching [IncludeModules].
             * @throws IllegalArgumentException if the provided type string doesn't match any enum entry.
             */
            public fun find(type: String): IncludeModules = entries
                .firstOrNull { entry -> entry.name.equals(type, ignoreCase = true) }
                ?: error(
                    "$type does not match with any entries of enum `${IncludeModules::class.java.canonicalName}`." +
                        " Valid choices are ${entries.map { it.name }}"
                )
        }
    }
}
