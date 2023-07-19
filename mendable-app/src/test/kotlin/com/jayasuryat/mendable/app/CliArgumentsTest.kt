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
package com.jayasuryat.mendable.app

import com.jayasuryat.mendable.app.FakeSystemExit.FakeSystemExitException
import com.jayasuryat.mendable.app.system.SystemExit
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder

internal class CliArgumentsTest {

    private val systemExit: SystemExit = FakeSystemExit()

    @Rule
    @JvmField
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @BeforeEach
    fun setup() {
        temporaryFolder.create()
    }

    @AfterEach
    fun tearDown() {
        temporaryFolder.delete()
    }

    @Test
    fun `should not throw error for all default values`() {

        val args = arrayOf<String>()

        shouldNotThrow<Throwable> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for empty scan path`() {

        val args = arrayOf("--composablesReportsPath", "")

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for empty output path`() {

        val args = arrayOf("--outputPath", "")

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for empty output file name`() {

        val args = arrayOf("--outputName", "")

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for invalid input file directory`() {

        val args = arrayOf("-i", "/randomPath/U4_3")

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for invalid input file`() {

        val file = temporaryFolder.newFile()
        val args = arrayOf("-i", file.path)

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should not throw error for valid input file`() {

        val file = temporaryFolder.newFolder()
        val args = arrayOf("-i", file.path)

        shouldNotThrow<Throwable> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for invalid output file directory`() {

        val args = arrayOf("-o", "/randomPath/U4_3")

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should throw error for invalid output file`() {

        val file = temporaryFolder.newFile()
        val args = arrayOf("-o", file.path)

        shouldThrow<FakeSystemExitException> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }

    @Test
    fun `should not throw error for valid output file`() {

        val file = temporaryFolder.newFolder()
        val args = arrayOf("-o", file.path)

        shouldNotThrow<Throwable> {

            CliArguments(
                args = args,
                systemExit = systemExit,
            )
        }
    }
}
