# Using `Mendable` as a `CLI` tool

!!! success inline end "Download"

    <a href="https://github.com/jayasuryat/mendable/releases/download/v0.7.0/mendable.jar"><img alt="Download Mendable" src="https://img.shields.io/badge/Mendable.jar-0.7.0-%2306090E?style=for-the-badge&logo=jetpackcompose"/></a>

It is very straightforward. Download and execute the `jar` file while **pointing it to the directory** which
contains all the Compose-compiler generated metrics files.

Mendable will take care of the rest. It will figure out metrics files of individual `modules`, `parse` them, `compute`
and `generate` a beautiful `report` for you.

!!! info

    `Mennable` does <b>not</b> generate `Compose compiler` `Metrics`, you would have to generate them and point
    `Mendable` to it's output. Read here to know [How to generate Compose Compiler Metrics](/others/HowToGenerate).
    The following steps assumes that you have a similar `gradle` setup as to the one described in the link above.

---

### ✨ Generate report with a single command ✨

[Download](https://github.com/jayasuryat/mendable/releases/download/v0.6.0/mendable-app.jar) and place the `jar` file in
the
same folder which contains all the generated Compose compiler metrics (should be `YourProject/build/compose_metrics`).
And then execute the `jar` file with the following `command`.

```
java -jar mendable-app.jar
```

After executing this `command` there should be `index.html` file generated in the same folder, which will contain the
combined metrics of all the modules.

---

### ✨Configuration ✨

While the above method is the easiest, and should work fine for most of the use cases, `Mendable` also supports some
configuration. The following are the supported options via `CLI arguments`.

```
java -jar mendable.jar
    --scanPaths, -i               [Default value : <Current working dir>] -> Paths to the directories containing the composables.txt files (for multiple paths use this format "path1 path2")
    --scanRecursively, -sr        [Default value : false]                 -> Weather to scan the directory recursively or not
    --outputPath, -o              [Default value : <Current working dir>] -> Report output directory
    --outputName, -oName          [Default value : "index"]               -> Name of the output file
    --exportType, -eType          [Default value : "html"]                -> Export type of the output file (html/json)
    --reportType, -rType          [Default value : "all"]                 -> Report type of the output file (all, with_warnings)
    --help, -h                                                            -> Usage info
```

For example :

=== "macOS"

    ```
    java -jar mendable.jar
        --scanRecursively \
        -i "/Users/username/Desktop/Your-project/build/compose_metrics /Users/username/Desktop/Other-project/build/compose_metrics" \
        -o /Users/username/Desktop/Reports \
        -oName Your-project-metrics \
        -eType html \
        -rType all \
    ```

=== "Windows"

    ```
    java -jar mendable.jar `
        --scanRecursively `
        -i "/Users/username/Desktop/Your-project/build/compose_metrics /Users/username/Desktop/Other-project/build/compose_metrics" `
        -o /Users/username/Desktop/Reports `
        -oName Your-project-metrics `
        -eType html `
        -rType all `
    ```

For the above command, files will be `read` from `/Users/username/Desktop/Your-project/build/compose_metrics`
and `/Users/username/Desktop/Other-project/build/compose_metrics` by recurisvely going through every directory in these
directories and the `output` file will be `saved` at `/Users/username/Desktop/Reports/Your-project-metrics.html`.

---

!!! note

    While `Mendable` has capabilities to `scan`/`parse` all the different types of `metrics files`. The `HTML`/`JSON`
    report only targets the `<module>-composables.txt` files.

??? info "And your <code>HTML</code> report should look something like this"

    ![Mendable sample screenshot](https://user-images.githubusercontent.com/37530409/206190055-33332a9c-f953-40d0-82a7-8d8df5d796f0.png)

---

### Other things to know

* Clicking on the composable's name in the `HTML` report will copy the name to the `clipboard`

* You can build an executable `jar` yourself by executing the following command in the root of the mendable project

```
./gradlew mendable-app:clean mendable-app:jar
```
