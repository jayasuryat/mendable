# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## Unreleased

> Upcoming

## [v0.7.0](https://github.com/jayasuryat/mendable/releases/tag/0.7.0) - 2023-10-13

### Added

- ([#28](https://github.com/jayasuryat/mendable/issues/28)) Support for `ModuleFactory` while scanning files via `scanFor<>Files()` methods
- ([#39](https://github.com/jayasuryat/mendable/issues/39)) Support for multiple `scanPaths` in report generation
- ([#42](https://github.com/jayasuryat/mendable/issues/42)) Support for controlling module names computed in `MendableReportGenerator`

### Changed

- ([#31](https://github.com/jayasuryat/mendable/issues/31)) Change `ModuleFactory`'s signature to parse based on `File` instead of `filename`
- ([#38](https://github.com/jayasuryat/mendable/issues/38)) Create `directory` for `outputPath` if it does not exist
- ([#35](https://github.com/jayasuryat/mendable/issues/35)) Handle different `directory` `file separators` for different `Operating Systems`
- Rename CLI argument `composablesReportsPath` to `scanPaths` and accept multiple scan paths (space separated strings in quotes) in ([#40](https://github.com/jayasuryat/mendable/pull/40))
- Fix `license header` for `CssStyle.kt` in [3b77aa2](https://github.com/jayasuryat/mendable/commit/3b77aa2e6b065ab56b345cb8b12ca90505f871fd)

### Removed

- ([#29](https://github.com/jayasuryat/mendable/issues/29)) Removed overloaded `scanFor<>Files()` methods
- Remove `spotless` `pre-commit hook` in [e94d5da](https://github.com/jayasuryat/mendable/commit/e94d5da5283e18f0aae04d0c71791d667698815b)

### Deprecated

- [`MendableReportGeneratorRequest`'s constructor](https://github.com/jayasuryat/mendable/blob/964c51e63681ea04811b2e65cfffd9159e3cb1a1/mendable/src/main/java/com/jayasuryat/mendable/MendableReportGeneratorRequest.kt#L59) with single `scan path` parameter in ([#39](https://github.com/jayasuryat/mendable/issues/39))
- [`MendableReportGeneratorRequest.scanPath`](https://github.com/jayasuryat/mendable/blob/964c51e63681ea04811b2e65cfffd9159e3cb1a1/mendable/src/main/java/com/jayasuryat/mendable/MendableReportGeneratorRequest.kt#L50C16-L50C24) property in ([#39](https://github.com/jayasuryat/mendable/issues/39))

## [v0.6.1](https://github.com/jayasuryat/mendable/releases/tag/0.6.1) - 2023-08-20

> Compatibility updates

### Added

- ([#24](https://github.com/jayasuryat/mendable/issues/24)) Support for maven publishing on CI

### Changed

- ([#23](https://github.com/jayasuryat/mendable/issues/23)) Setup forward compatibility
- Artifacts are now `compatible` with `Kotlin 1.7.0`
- `JVM` `target-version` fixed to `1.8`

### Removed

- `.toString()` and `.equals()` methods on all `data objects` [got removed]((https://github.com/jayasuryat/mendable/commit/334af351c2568c900ea9f25c0451ea94f634e515))
- Stopped `kotlin-std-lib` from getting bundled into artifacts

## [v0.6.0](https://github.com/jayasuryat/mendable/releases/tag/0.6.0) - 2023-08-18

> Underlying components of Mendable are now available as maven-artifacts at MavenCentral

### Added

- ([#5](https://github.com/jayasuryat/mendable/issues/5))  Support for `parsing`/`scanning` **all** of the Jetpack Compose Compiler Metrics files
- ([#18](https://github.com/jayasuryat/mendable/issues/18)) Support for `maven publishing`
- ([#19](https://github.com/jayasuryat/mendable/issues/19)) `Documentation` on `Github-pages`
- ([#1](https://github.com/jayasuryat/mendable/issues/1))  Support for `JSON` export type, `Warning` inclusion type, and `Recursive File` scanning by [bsobe](https://github.com/bsobe) 🚀
- ([#13](https://github.com/jayasuryat/mendable/issues/13)) Support for `CI` `API validation`
- ([#9](https://github.com/jayasuryat/mendable/issues/9))  Support for dependency version management via `version-catalogs`

### Changed

- ([#4](https://github.com/jayasuryat/mendable/issues/4))  Modularise components into different Gradle modules
- ([#16](https://github.com/jayasuryat/mendable/issues/16)) Add `.toString()`, `.equals()` methods to classes in public APIs / Adopt [`Poko`](https://github.com/drewhamilton/Poko)
- ([#11](https://github.com/jayasuryat/mendable/issues/11)) Separate out `type` and `default value`in `ComposableSignaturesReport`
- ([#14](https://github.com/jayasuryat/mendable/issues/14)) Add missing `docs` for all the public API
- ([#2](https://github.com/jayasuryat/mendable/issues/2))  Update `workflow` `runner` to ubuntu-latest
- ([#10](https://github.com/jayasuryat/mendable/issues/10)) Bump up dependency versions
- ([#3](https://github.com/jayasuryat/mendable/issues/3))  Print logs for failing `tests`

## [v0.5.0](https://github.com/jayasuryat/mendable/releases/tag/0.5.0) - 2022-12-07

> Initial release

### Added

- Mendable - A CLI tool to convert multi-module Jetpack Compose compiler metrics into beautiful HTML reports
