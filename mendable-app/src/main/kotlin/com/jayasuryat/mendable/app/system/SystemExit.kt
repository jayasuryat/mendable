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
package com.jayasuryat.mendable.app.system

import kotlin.system.exitProcess

/**
 * Represents an interface for handling system exit operations.
 */
internal interface SystemExit {

    /**
     * Exits the system with the specified status code.
     *
     * @param status The status code to exit the system with.
     * @return [Nothing] This function always returns [Nothing] to indicate an immediate exit.
     */
    fun exit(status: Int): Nothing
}

/**
 * Provides the default implementation of the SystemExit interface using the exitProcess function.
 */
internal class DefaultSystemExit : SystemExit {

    /**
     * Exits the system with the specified status code.
     *
     * @param status The status code to exit the system with.
     * @return [Nothing] This function always returns [Nothing] to indicate an immediate exit.
     */
    override fun exit(status: Int): Nothing {
        exitProcess(status)
    }
}
