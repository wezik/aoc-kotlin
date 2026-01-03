package app.wezik.aoc.domain

import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId

@JvmInline
value class Day(val value: Int) {
    init {
        require(value in 1..25) { "Day must be between 1 and 25" }
    }
}

@JvmInline
value class Year(val value: Int) {

    init {
        require(value >= 2015) { "Advent of code started in 2015" }
    }

    companion object {
        // resolves to most up-to-date valid year of advent of code
        fun recent(): Year {
            // advent of code launches at midnight EST time (1st day of December)
            val estZoneId = ZoneId.of("America/New_York")
            val zonedDateTime = LocalDateTime.now(estZoneId)
            return Year(if (zonedDateTime.monthValue == 12) zonedDateTime.year else zonedDateTime.year - 1)
        }
    }

}

// wrapper class that should make it easier to extend later, 
// preloads the content so it doesnt effect the performance
class SolutionInput(file: File, val isTestRun: Boolean = false) {
    val content = file.readText()
    val lines = file.readLines()
}

sealed interface SolutionResult {
    data class Success(val output: Any) : SolutionResult
    data class NotImplemented(val reason: String? = null) : SolutionResult

    fun isSuccess() = this is Success
    fun isNotImplemented() = this is NotImplemented
}


open class Solution() {
    open fun part1(input: SolutionInput): SolutionResult = SolutionResult.NotImplemented()
    open fun part2(input: SolutionInput): SolutionResult = SolutionResult.NotImplemented()
}
