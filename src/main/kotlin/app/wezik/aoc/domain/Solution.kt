package app.wezik.aoc.domain

import java.io.File
import java.time.ZoneId
import java.time.LocalDateTime

@JvmInline
value class Day(val value: Int)

@JvmInline
value class Year(val value: Int) {

    companion object {
        // NOTE: resolves to most up-to-date valid year of advent of code
        fun recent(): Year {
            // NOTE: advent of code launches at midnight EST time (1st day of December)
            val estZoneId = ZoneId.of("America/New_York")
            val zonedDateTime = LocalDateTime.now(estZoneId)
            return Year(if (zonedDateTime.monthValue == 12) zonedDateTime.year else zonedDateTime.year - 1)
        }
    }

}

// NOTE: wrapper class that should make it easier to extend later, 
// also preloads the content so it doesnt effect the performance
class SolutionInput(file: File) {
    val content = file.readText()
    val lines = file.readLines()
}

// NOTE: similar structure to Result but with added context
sealed interface SolutionResult {
    data class Success(val output: Any) : SolutionResult
    data class Failure(val error: Throwable) : SolutionResult
    object NotImplemented : SolutionResult

    fun isFailure() = this is Failure
    fun isSuccess() = this is Success
    fun isNotImplemented() = this === NotImplemented
}


open class Solution() {
    fun part1Runner(input: SolutionInput) = try { part1(input) } catch (e: Throwable) { SolutionResult.Failure(e) }
    fun part2Runner(input: SolutionInput) = try { part2(input) } catch (e: Throwable) { SolutionResult.Failure(e) }

    open protected fun part1(input: SolutionInput): SolutionResult = SolutionResult.NotImplemented
    open protected fun part2(input: SolutionInput): SolutionResult = SolutionResult.NotImplemented
}
