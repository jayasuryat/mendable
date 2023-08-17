<div align="center">

  <a>
    <picture>
      <img alt="Mendable - CLI tool and libraries to scan, parse and render Jetpack Compose compiler metrics as beautiful HTML reports"
src="https://github.com/jayasuryat/mendable/assets/37530409/a0ef23d5-fee2-4e8a-bab3-1483b97121b9" height="200">
    </picture>
  </a>

  <h1>CLI tool and libraries to<br><code>scan</code>, <code>parse</code> and <code>render</code><br><code>Jetpack Compose</code> compiler <code>metrics</code><br>as beautiful <code>HTML</code> & <code>JSON</code> reports</h1>

</div>

---

!!! tip "Fastforward?"

    [Jump to `How to use`](howto/index.md)

---

## Why Mendable?

If any of the following sounds like one of your requirements:

1. `Scan` and find `Compose Compiler metrics` files in a directory
2. `Parse` `metrics files` into usable, operable `Kotlin objects`
3. `Render` metrics as `HTML` / `JSON` reports
4. Do all of the above with a single command in `CLI` as well?

then `Mendable` has you covered.

`Mendable` `scans` & `parses` `Compose compiler` generated `metrics files` and renders them as beautiful `HTML`/`JSON`
reports.`Mendable` focuses on problematic `composable methods`, highlighting issues causing parts and filtering out
non-problematic ones. It consolidates reports from multiple `modules` into a single, well-organized `HTML` page or a
`JSON` report, streamlining the `workflow` of finding/fixing `unstable` `Composables`.

!!! info ""

    The `scan`, `parse` & `report` parts of `Mendable` are published as artifacts on `Maven Central`. If you
    are building any `tooling`/`libraries` arround Compose Compiler Metrics these capabilities of `Mendable` could give
    you a significant headstart.

---

<div align="center">
  <h3>And your <code>HTML</code> report should look something like this</h3>
</div>

![Mendable sample screenshot](https://user-images.githubusercontent.com/37530409/206190055-33332a9c-f953-40d0-82a7-8d8df5d796f0.png)
