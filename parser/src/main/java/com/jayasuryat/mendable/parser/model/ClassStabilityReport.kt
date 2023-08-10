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
package com.jayasuryat.mendable.parser.model

import com.jayasuryat.mendable.metricsfile.Module

/**
 * Represents a report containing stability information for classes in a module. Includes details about each class's
 * stability-conditions, runtime-stability, and fields.
 *
 * Generally represents files with name in `<module-name>-classes.txt` format.
 *
 * @property module The module to which this stability report pertains.
 * @property classes The list of class details in this stability report.
 */
public class ClassStabilityReport(
    public override val module: Module,
    public val classes: List<ClassDetails>,
) : ComposeCompilerMetrics {

    /**
     * Represents detailed information about a class's stability and fields.
     *
     * @property name The name of the class.
     * @property condition The stability condition of the class.
     * @property runtimeStability The runtime stability of the class.
     * @property fields The list of fields within the class.
     */
    public class ClassDetails(
        public val name: String,
        public val condition: Condition,
        public val runtimeStability: RuntimeStability?,
        public val fields: List<Field>,
    ) {

        /**
         * Represents a field within a class, including its stability and declaration type.
         *
         * @property name The name of the field.
         * @property condition The stability condition of the field.
         * @property declarationType The declaration type (Val or Var) of the field.
         * @property type The type of the field.
         */
        public class Field(
            public val name: String,
            public val condition: Condition,
            public val declarationType: DeclarationType,
            public val type: String,
        ) {

            /**
             * Represents the declaration type (Val or Var) of a field.
             */
            public enum class DeclarationType {
                Val,
                Var,
                ;

                public companion object
            }

            public companion object
        }

        /**
         * Represents the runtime stability status of a class.
         */
        public sealed interface RuntimeStability {

            public data object Stable : RuntimeStability
            public data object Unstable : RuntimeStability

            /**
             * Represents a class with uncertain runtime stability due to a specific cause.
             *
             * @property cause The cause of uncertain runtime stability.
             */
            public class Uncertain(
                public val cause: String,
            ) : RuntimeStability

            /**
             * Represents a class with runtime stability affected by a parameter.
             *
             * @property cause The cause of runtime stability being parameter-dependent.
             */
            public class Parameter(
                public val cause: String,
            ) : RuntimeStability

            public companion object
        }

        public companion object
    }

    /**
     * Represents the stability condition of a class or a field.
     */
    public enum class Condition {
        Stable,
        Unstable,
        Runtime,
        ;

        public companion object
    }

    public companion object
}
