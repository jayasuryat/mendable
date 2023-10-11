# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## Unreleased
// All of the other changes
// composablesReportsPath renamed to scanPaths
// scanPath renamed to scanPaths
> Upcoming

## [v0.6.1](https://github.com/jayasuryat/mendable/releases/tag/0.6.1) - 2022-08-20

> Compatibility updates

### Added

- ([#24](https://github.com/jayasuryat/mendable/issues/24)) Support for maven publishing on CI
  by [jayasuryat](https://github.com/jayasuryat)
  in https://github.com/jayasuryat/mendable/pull/26

### Changed

- ([#23](https://github.com/jayasuryat/mendable/issues/23)) Setup forward compatibility
  by [jayasuryat](https://github.com/jayasuryat) in #25
- Artifacts are now `compatible` with `Kotlin 1.7.0`
- `JVM` `target-version` fixed to `1.8`

### Removed

- `.toString()` and `.equals()` methods on
  all `data objects` [got removed]((https://github.com/jayasuryat/mendable/commit/334af351c2568c900ea9f25c0451ea94f634e515))
- Stopped `kotlin-std-lib` from getting bundled into artifacts

## [v0.6.0](https://github.com/jayasuryat/mendable/releases/tag/0.6.0) - 2023-08-18

> Underlying components of Mendable are now available as maven-artifacts at MavenCentral

### Added

- ([#5](https://github.com/jayasuryat/mendable/issues/5))  Support for `parsing`/`scanning` **all** of the Jetpack
  Compose Compiler Metrics files by [jayasuryat](https://github.com/jayasuryat)
- ([#18](https://github.com/jayasuryat/mendable/issues/18)) Support for `maven publishing`
- ([#19](https://github.com/jayasuryat/mendable/issues/19)) `Documentation` on `Github-pages`
- ([#1](https://github.com/jayasuryat/mendable/issues/1))  Support for `JSON` export type, `Warning` inclusion type,
  and `Recursive File` scanning by [bsobe](https://github.com/bsobe) 🚀
- ([#13](https://github.com/jayasuryat/mendable/issues/13)) Support for `CI` `API validation`
- ([#9](https://github.com/jayasuryat/mendable/issues/9))  Support for dependency version management
  via `version-catalogs`

### Changed

- ([#4](https://github.com/jayasuryat/mendable/issues/4))  Modularise components into different Gradle modules
- ([#16](https://github.com/jayasuryat/mendable/issues/16)) Add `.toString()`, `.equals()` methods to classes in public
  APIs / Adopt [`Poko`](https://github.com/drewhamilton/Poko)
- ([#11](https://github.com/jayasuryat/mendable/issues/11)) Separate out `type` and `default value`
  in `ComposableSignaturesReport`
- ([#14](https://github.com/jayasuryat/mendable/issues/14)) Add missing `docs` for all the public API
- ([#2](https://github.com/jayasuryat/mendable/issues/2))  Update `workflow` `runner` to ubuntu-latest
- ([#10](https://github.com/jayasuryat/mendable/issues/10)) Bump up dependency versions
- ([#3](https://github.com/jayasuryat/mendable/issues/3))  Print logs for failing `tests`

## [v0.5.0](https://github.com/jayasuryat/mendable/releases/tag/0.5.0) - 2022-12-07

> Initial release

### Added

- Mendable - A CLI tool to convert multi-module Jetpack Compose compiler metrics into beautiful HTML reports
