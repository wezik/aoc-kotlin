package app.wezik.aoc2024.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun time(block: () -> Unit): Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}

fun Long.toNanoDuration(): Duration = this.toDuration(DurationUnit.NANOSECONDS)
