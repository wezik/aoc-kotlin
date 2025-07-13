package app.wezik.aoc.domain

import app.wezik.aoc.domain.SolutionResult.*
import com.github.ajalt.clikt.core.UsageError
import kotlin.time.measureTime

data class SolutionRunResult(
    val part1: SolutionResult,
    val part2: SolutionResult,
)

class SolutionRunner(
    private val solutionSelector: SolutionSelector,
    private val inputResolver: InputResolver,
    private val echo: (Any?) -> Unit,
) {

    fun run(context: DefaultContext) : SolutionRunResult {
        val (day, year, runP1, runP2, sessionCookie) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day ${day.value} of ${year.value} is not implemented")
        val input = inputResolver.fetchAdventInput(day.value, year.value, sessionCookie)
        return solution.run(runP1, runP2, input)
    }

    fun run(context: ExampleContext) : SolutionRunResult {
        val (day, year, runP1, runP2) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day ${day.value} of ${year.value} is not implemented")
        val input = inputResolver.fetchExampleInput(day.value, year.value)
        return solution.run(runP1, runP2, input)
    }

    fun run(context: CustomContext) : SolutionRunResult {
        val (day, year, runP1, runP2, path) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day ${day.value} of ${year.value} is not implemented")
        val input = inputResolver.fetchCustomInput(day.value, year.value, path)
        return solution.run(runP1, runP2, input)
    }

    // NOTE: a bit excessive for extension function, but it sorta just times the execution, logs it and collects the results
    private fun Solution.run(runP1: Boolean, runP2: Boolean, input: SolutionInput) : SolutionRunResult {
        var p1Result: SolutionResult = NotImplemented
        if (runP1) {
            val part1Duration = measureTime { p1Result = part1Runner(input) }

            when (p1Result) {
                is Success -> echo("Part 1: ${p1Result.output} ($part1Duration)")
                is Failure -> echo("Part 1 failed with error: ${p1Result.error.message}").also { p1Result.error.printStackTrace() }
                is NotImplemented -> echo("Part 1 not implemented")
            }
        }

        var p2Result: SolutionResult = NotImplemented
        if (runP2) {
            val part2Duration = measureTime { p2Result = part2Runner(input) }
            when (p2Result) {
                is Success -> echo("Part 2: ${p2Result.output} ($part2Duration)")
                is Failure -> echo("Part 2 failed with error: ${p2Result.error.message}").also { p2Result.error.printStackTrace() }
                is NotImplemented -> echo("Part 2 not implemented")
            }
        }

        return SolutionRunResult(p1Result, p2Result)
    }
}
