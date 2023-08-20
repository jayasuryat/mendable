<div align="center">

  <a>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/37530409/205920274-d4cd2c4e-92d9-40d8-ac0f-39e8374600d6.svg" height="200">
      <img alt="Dowel banner" src="https://user-images.githubusercontent.com/37530409/205920279-1c22ea9e-1f81-45d9-9994-01785e9ab473.svg" height="200">
    </picture>
  </a>

  <h1>CLI tool and libraries to<br><code>scan</code>, <code>parse</code> and <code>render</code><br><code>Jetpack Compose</code> compiler <code>metrics</code><br>as beautiful <code>HTML</code> & <code>JSON</code> reports</h1>

 <p align="center">
      <a href="https://repo1.maven.org/maven2/com/jayasuryat/mendable/"><img alt="Mendable at MavenCentral" src="https://staging.shields.io/maven-central/v/com.jayasuryat.mendable/mendable?style=for-the-badge&color=%2332cc57"/></a>
    <a href="https://github.com/jayasuryat/mendable/releases/download/v0.6.0/mendable-app.jar"><img alt="Download Mendable" src="https://img.shields.io/badge/Mendable.jar-0.6.0-%2306090E?style=for-the-badge&logo=jetpackcompose&color=%2332cc57"/></a>
  </p>

  <p align="center">
    <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
    <a href="https://github.com/jayasuryat/mendable/actions/workflows/main.yml"><img alt="CI" src="https://github.com/jayasuryat/mendable/actions/workflows/main.yml/badge.svg"/></a>
  </p>

  <p align="center">
    <a href="https://androidweekly.net/issues/issue-548"><img alt="AndroidWeekly" src="https://img.shields.io/badge/AndroidWeekly-%23548-2299cc.svg?style=flat&logo=android"/></a>
    <a href="https://mailchi.mp/kotlinweekly/kotlin-weekly-332"><img alt="KotlinWeekly" src="https://img.shields.io/badge/KotlinWeekly-%23332-7549b5.svg?style=flat&logo=kotlin"/></a>
    <a href="https://jetc.dev/issues/145.html"><img alt="jetc.dev" src="https://img.shields.io/badge/jetc.dev-%23145-343a40.svg?style=flat&logo=jetpackcompose"/></a>
  </p>

</div>

## 1. What is `Mendable`?

It is a set of `libraries` and a `CLI tool` to make working with `Compose-compiler` `metrics` easier. [Read the
full documentation here](https://jayasuryat.github.io/mendable/).

## 2. Do you need `Mendable`?

If you need to:

1. Find `Compose Compiler metrics` by `scanning` a directory
2. `Parse` `metrics` into usable `Kotlin objects`
3. Generate glanceable and digestible `HTML` / `JSON` reports
4. Be able to do all of this in a `CLI` as well?

then `Mendable` has you covered.

`Mendable` `scans` & `parses` `Compose compiler` generated `metrics files` and renders them as beautiful `HTML`/`JSON`
reports.`Mendable` focuses on problematic `composable methods`, highlighting issues causing parts and filtering out
non-problematic ones. It consolidates reports from multiple `modules` into a single, well-organized `HTML` page or a
`JSON` report, streamlining the `workflow` of finding/fixing `unstable` `Composables`.

> The `scan`, `parse` & `report` parts of `Mendable` are published as artifacts at `Maven Central`. If you
> are building any `tooling`/`libraries` arround Compose Compiler Metrics these capabilities of `Mendable` could give
> you a significant headstart.

## 3. How to use Mendable?

`Mendable` can primarily be used in two different ways:

1. As a `CLI` tool
2. As a set of library dependencies in your `Kotlin` / `Java` projects

#### 3.1 Download Mendable CLI tool

<a href="https://github.com/jayasuryat/mendable/releases/download/v0.6.0/mendable-app.jar"><img alt="Download Mendable" src="https://img.shields.io/badge/Mendable.jar-0.6.0-%2306090E?style=for-the-badge&logo=jetpackcompose&color=%2332cc57"/></a>

#### 3.2 Or, download Mendable Maven Artifacts

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.jayasuryat.mendable:scanner:0.6.1")
    implementation("com.jayasuryat.mendable:parser:0.6.1")
    implementation("com.jayasuryat.mendable:mendable:0.6.1")
}
```

<div align="center">
  <h3><a href="https://jayasuryat.github.io/mendable/howto/">🚀  Read the documentation here to get started</a></h3>
</div>

---
<div align="center">
  <h3>Your Compose-compiler metrics could look something like this</h3>
</div>

![Mendable sample screenshot](https://user-images.githubusercontent.com/37530409/206190055-33332a9c-f953-40d0-82a7-8d8df5d796f0.png)

## Changes

Prior to `v0.6.0`, `Mendable` was only available as a `CLI tool`. Since `v0.6.0` the underlying components have been
`modularised` and published to `maven central`. You can see the full list of
changes [here](https://github.com/jayasuryat/mendable/releases/tag/v0.6.0).

## Contributions

Contributions are super welcome!
See [Contributing Guidelines](https://github.com/jayasuryat/mendable/blob/main/CONTRIBUTING.md).

## Discussions

Have any questions or queries, or want to discuss anything? Feel free
and [start a discussion](https://github.com/jayasuryat/mendable/discussions).

## License

```
 Copyright 2022 Jaya Surya Thotapalli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```