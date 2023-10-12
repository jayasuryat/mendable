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
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.io.File

internal class DefaultModuleFactoryTest {

    private val factory: ModuleFactory = DefaultModuleFactory()

    @Test
    fun `should parse correctly for simple composables-txt file`() {

        val file = File("app_qaRelease-composables.txt")
        val module: Module = factory.parseModule(
            file = file,
        )

        module.name shouldBe "app"
        module.buildVariant shouldBe "qaRelease"
    }

    // While the expected outputs of this test are not really what one might expect. This test is just present to
    // demonstrate that this behavior is known.
    // And the reason is documented in the factory class.
    @Test
    fun `should parse correctly for complex input`() {

        val file = File("feature_a_build_variant-composables.txt")
        val module: Module = factory.parseModule(
            file = file,
        )

        module.name shouldBe "feature_a_build"
        module.buildVariant shouldBe "variant"
    }

    @Test
    fun `should parse correctly for simple compose_release-classes-txt file`() {

        val file = File("compose_release-classes.txt")
        val module: Module = factory.parseModule(
            file = file,
        )

        module.name shouldBe "compose"
        module.buildVariant shouldBe "release"
    }

    @Test
    fun `should parse correctly for simple discover_release-composables-csv file`() {

        val file = File("discover_release-composables.csv")
        val module: Module = factory.parseModule(
            file = file,
        )

        module.name shouldBe "discover"
        module.buildVariant shouldBe "release"
    }

    @Test
    fun `should parse correctly for search_release-module-json file`() {

        val file = File("search_release-module.json")
        val module: Module = factory.parseModule(
            file = file,
        )

        module.name shouldBe "search"
        module.buildVariant shouldBe "release"
    }

    @Test
    fun `should parse correctly for a bunch of cases`() {

        val buildVariants = listOf(
            "release",
            "qaRelease",
            "standardRelease",
        ).sorted()

        val names = listOf(
            "discover",
            "trending",
            "showdetails",
        ).sorted()

        val suffixes = allPostfixes

        names.forEach { name ->
            buildVariants.forEach { variant ->
                suffixes.forEach { type ->

                    val fileName = "${name}_${variant}$type"
                    val module = factory.parseModule(
                        file = File(fileName),
                    )

                    module.name shouldBe name
                    module.buildVariant shouldBe variant
                }
            }
        }
    }
}
