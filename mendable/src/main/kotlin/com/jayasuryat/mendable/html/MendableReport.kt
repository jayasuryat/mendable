/*
 * Copyright 2022 Jaya Surya Thotapalli
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
@file:Suppress("FunctionName")

package com.jayasuryat.mendable.html

import com.jayasuryat.mendable.model.ComposablesReport
import com.jayasuryat.mendable.model.ComposablesReport.ModuleReport
import kotlinx.html.*
import kotlinx.html.stream.createHTML

internal fun MendablePage(
    report: ComposablesReport,
): String {

    return createHTML().html {

        head {
            title("Mendable") // TODO: Take app name as a param
            ResourceLinking()
            PageStyle()
        }

        body {

            h1 { +"Mendable" }

            // Overview section
            Overview(
                report = report,
            )

            // Divider
            hr("rounded")

            // Individual module reports
            ModuleReports(
                reports = report.moduleReports,
            )

            Footer()

            Scripts()
        }
    }
}

private fun BODY.ModuleReports(
    reports: List<ModuleReport>,
) {

    reports.forEach { report ->

        ModuleReport(
            report = report,
        )

        div {
            setStyle(margin = "0 0 32px 0")
        }
    }
}

private fun BODY.Footer() {

    h1("footer") {
        br { }
        +"Made with <3 - "
        a(href = "https://github.com/jayasuryat/mendable") { +"Mendable" }
    }
}
