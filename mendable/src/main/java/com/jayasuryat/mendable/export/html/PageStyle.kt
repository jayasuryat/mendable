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

import kotlinx.html.HEAD
import kotlinx.html.style
import kotlinx.html.unsafe

@Suppress("FunctionName")
internal fun HEAD.PageStyle() {

    val rawCss =
        """
        body {
            background-color: ${Colors.background};
            padding: 32px;
        }

        h1 {
            color: ${Colors.onBackground};
            text-align: center;
            font-family: 'Inconsolata', monospace;
            font-size: 64px;
            font-weight: bold;
        }

        .overview-div {

            padding: 32px;

            margin: 0 auto 32px auto;
            width: 50%;

            border: 8px solid ${Colors.primary};
            border-radius: 32px;

            color: ${Colors.onBackground};
            text-align: center;
            font-family: 'Inconsolata', monospace;
            font-size: 32px;
            font-weight: normal;

            box-shadow: 0 16px 32px 0 rgba(0,0,0,0.2);
        }

        progress {
            height: 16px;
            width: 100%;
            border: 0;
            border-radius: 16px;
            background: ${Colors.success};
        }
        progress {
            height: 16px;
            border: 0;
            border-radius: 16px;
            color: ${Colors.primary};
        }
        progress::-moz-progress-bar {
            height: 16px;
            border: 0;
            border-radius: 16px;
            background: lightcolor;
        }
        progress::-webkit-progress-value {
            height: 16px;
            border: 0;
            border-radius: 16px;
            background: ${Colors.success};
        }
        progress::-webkit-progress-bar {
            height: 16px;
            border: 0;
            border-radius: 16px;
            background: ${Colors.primary};
        }

        .grid-container {
            display: grid;
            grid-template-columns: 33% 33% 33%;
            margin: 0 32px 0 32px;
        }

        .grid-item {

            position: relative;

            background-color: ${Colors.background};
            border: 4px solid ${Colors.primary};
            border-radius: 32px;

            margin: 28px 14px 0px 14px;
            padding: 32px;
            font-size: 24px;
            text-align: left;

            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            transition: 0.3s;
        }

        .grid-item:hover {
          box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
        }

        .link-spanner {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            z-index: 1;
        }

        .module-overview-title {
            color: ${Colors.onBackground};
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 24px;
            font-weight: 900;
        }

        .module-overview-details {
            color: ${Colors.onBackground};
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 24px;
            font-weight: normal;
        }

        hr.rounded {
          border: 8px solid ${Colors.primary};
          border-radius: 8px;
          margin: 64px 0 64px 0;
          //box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2);
        }

        .module-report-title {
            margin: 0 32px 0 32px;
            padding: 32px;
            background-color: ${Colors.primary};
            color: ${Colors.onBackground};
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 32px;
            font-weight: bold;
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        }

        .module-report-body {
            margin : 0 32px 0 32px;
            border: 4px solid ${Colors.primary};
            padding : 32px;
            border-bottom-left-radius: 16px;
            border-bottom-right-radius: 16px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        }

        .module-report-empty-body {
            margin : 0 32px 0 32px;
            border: 4px solid ${Colors.primary};
            padding : 32px;
            border-bottom-left-radius: 16px;
            border-bottom-right-radius: 16px;

            color: ${CodeColors.default};
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 18px;
            font-weight: normal;

            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        }

        .composable-title {
            color: #F7C06A;
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 24px;
            font-weight: bold;
        }

        .click-to-copy {
            position: relative;
            display: inline-block;
            cursor: pointer;
            color: #F7C06A;
            text-align: left;
            font-family: 'Inconsolata', monospace;
            font-size: 24px;
            font-weight: bold;
        }
        .click-to-copy .tooltip-text {
            visibility: hidden;
            width: 120px;
            background-color: ${Colors.primary};
            color: ${Colors.onBackground};
            text-align: center;
            border-radius: 8px;
            padding: 8px;

            font-family: 'Inconsolata', monospace;
            font-size: 16px;
            font-weight: normal;

            /* Position the tooltip */
            position: absolute;
            z-index: 1;
            bottom: 100%;
            left: 50%;
            margin-left: -60px;
        }
        .click-to-copy:hover .tooltip-text {
            visibility: visible;
        }

        .code {
            color: ${CodeColors.default};
            text-align: left;
            font-family: "JetBrains Mono", monospace;
            font-size: 16px;
            font-weight: normal;
        }

        .code-container {
            width: auto;
            display: block;
            overflow: scroll;
            margin: 8px 0 0px 0;
            padding: 0 16px 0 16px;
            border: 1px solid ${Colors.primary};
            border-radius: 8px;
            //background: #282828;
            background: #2a2a2a;
        }

        .footer {
            color: ${Colors.onBackground};
            text-align: center;
            font-family: 'Inconsolata', monospace;
            font-size: 20px;
            font-weight: bold;
        }
        .footer a{
            color: inherit;
        }
        """.trimIndent()

    style { unsafe { raw(rawCss) } }
}
