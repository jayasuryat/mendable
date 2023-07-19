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

import kotlinx.html.BODY
import kotlinx.html.script
import kotlinx.html.unsafe

internal fun BODY.Scripts() {

    val script = """
        var elements = document.getElementsByClassName("function-name");
        for (var i = 0; i < elements.length; i++) {
            const element = elements[i]
            const text = element.innerHTML
            elements[i].addEventListener("click", function() {
                navigator.clipboard.writeText(text.trim());
            });
        }
    """.trimIndent()

    script { unsafe { raw(script) } }
}
