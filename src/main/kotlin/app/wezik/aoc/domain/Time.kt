package app.wezik.aoc.domain

import kotlin.time.Duration
import kotlin.time.measureTime

fun <T> timedRun(block: () -> T): Pair<Result<T>, Duration> {
    var result: Result<T>? = null
    val duration = measureTime {
        result = runCatching { block() }
    }
    // it's technically not possible for it to be null, also delegate error handling to the caller
    return result!! to duration
}
