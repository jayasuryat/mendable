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

package com.jayasuryat.mendable.export.html

import kotlinx.html.HEAD
import kotlinx.html.link

internal fun HEAD.ResourceLinking() {
    FontsLinking()
}

private fun HEAD.FontsLinking() {

    link(
        rel = "preconnect",
        href = "https://fonts.googleapis.com",
    )
    link(
        rel = "preconnect",
        href = "https://fonts.gstatic.com",
        type = "crossorigin",
    )
    link(
        rel = "stylesheet",
        href = "https://fonts.googleapis.com/css2?family=Inconsolata:wght@200;300;400;500;600;700;800;900&display=swap",
    )
    link(
        rel = "stylesheet",
        href = "https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap",
    )
}
