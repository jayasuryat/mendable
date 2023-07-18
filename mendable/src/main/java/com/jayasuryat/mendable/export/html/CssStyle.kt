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
package com.jayasuryat.mendable.export.html

import kotlinx.html.HTMLTag
import kotlinx.html.impl.DelegatingMap

internal fun HTMLTag.setStyle(
    color: String? = null,
    backgroundColor: String? = null,
    fontSize: String? = null,
    textAlign: String? = null,
    width: String? = null,
    margin: String? = null,
    padding: String? = null,
) {
    attributes.setStyle(
        color = color,
        backgroundColor = backgroundColor,
        fontSize = fontSize,
        textAlign = textAlign,
        width = width,
        margin = margin,
        padding = padding
    )
}

private fun DelegatingMap.setStyle(
    color: String? = null,
    backgroundColor: String? = null,
    fontSize: String? = null,
    textAlign: String? = null,
    width: String? = null,
    margin: String? = null,
    padding: String? = null,
) {
    set(
        "style",
        buildStyle(
            color = color,
            backgroundColor = backgroundColor,
            fontSize = fontSize,
            textAlign = textAlign,
            width = width,
            margin = margin,
            padding = padding
        )
    )
}

private fun buildStyle(
    color: String? = null,
    backgroundColor: String? = null,
    fontSize: String? = null,
    textAlign: String? = null,
    width: String? = null,
    margin: String? = null,
    padding: String? = null,
): String = buildString {
    color?.let { append(styleProperty(StyleProps.Color, color)) }
    backgroundColor?.let { append(styleProperty(StyleProps.BackgroundColor, backgroundColor)) }
    fontSize?.let { append(styleProperty(StyleProps.FontSize, fontSize)) }
    textAlign?.let { append(styleProperty(StyleProps.TextAlign, textAlign)) }
    width?.let { append(styleProperty(StyleProps.Width, width)) }
    margin?.let { append(styleProperty(StyleProps.Margin, margin)) }
    padding?.let { append(styleProperty(StyleProps.Padding, padding)) }
}

private fun styleProperty(property: String, value: String) = "$property: $value;"

private object StyleProps {
    const val Color = "color"
    const val BackgroundColor = "background-color"
    const val FontSize = "font-size"
    const val TextAlign = "text-align"
    const val Width = "width"
    const val Margin = "margin"
    const val Padding = "padding"
}
