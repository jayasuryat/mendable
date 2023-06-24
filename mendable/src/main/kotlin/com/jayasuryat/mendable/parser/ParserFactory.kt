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
package com.jayasuryat.mendable.parser

import com.jayasuryat.mendable.IncludeModules

internal fun Parser.Companion.create(
    includeModules: IncludeModules,
): Parser {

    return when (includeModules) {

        IncludeModules.ALL -> ComposableReportParser()

        IncludeModules.WITH_WARNINGS -> WarningsOnlyReportParser(
            backingParser = ComposableReportParser(),
        )
    }
}
