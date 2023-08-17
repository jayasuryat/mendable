The [`Compose`](https://developer.android.com/jetpack/compose) `Compiler plugin` can generate `reports/metrics` around
certain Compose-specific concepts that can be useful in understanding what is happening with some of the `Compose` code
at a fine-grained level.

It can output various performance-related `metrics` at build time, allowing us to peek behind the curtains and see where
any potential `performance issues` are.

Reed more about how to interpret these metrics [here](https://chrisbanes.me/posts/composable-metrics/#interpreting-the-reports) & [here](https://github.com/androidx/androidx/blob/androidx-main/compose/compiler/design/compiler-metrics.md#things-to-look-out-for)
