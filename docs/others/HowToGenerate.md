 Add the following lines to your **root project's** `build.gradle` file. This will direct the Compose compiler to
 generate metrics and save all of them into the **root project's** `build folder` (for all of the `modules`).

=== "Kotlin"
    ```kotlin title="build.gradle.kts (root)"
    allprojects {
        tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class.java).configureEach {
            kotlinOptions {
                // Trigger this with:
                // ./gradlew assembleRelease -PenableMultiModuleComposeReports=true --rerun-tasks
                if (project.findProperty("enableMultiModuleComposeReports") == "true") {
                    freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
                    freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
                }
            }
        }
    }
    ```
=== "Groovy"
    ``` groovy title="build.gradle (root)"
    subprojects {
        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
            kotlinOptions {
                // Trigger this with:
                // ./gradlew assembleRelease -PenableMultiModuleComposeReports=true --rerun-tasks
                if (project.findProperty("enableMultiModuleComposeReports") == "true") {
                    freeCompilerArgs += ["-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/"]
                    freeCompilerArgs += ["-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/"]
                }
            }
        }
    }
    ```

With the above setup, you can generate Compose compiler metrics by executing the following `command` in the `terminal`
window.

```
./gradlew assembleRelease -PenableMultiModuleComposeReports=true --rerun-tasks
```
