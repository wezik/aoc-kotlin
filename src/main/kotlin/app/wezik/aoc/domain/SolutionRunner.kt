package app.wezik.aoc.domain

import com.github.ajalt.clikt.core.UsageError
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

data class SolutionResult(
    val p1: String?,
    val p2: String?,
)

class SolutionRunner(
    private val solutionSelector: SolutionSelector,
    private val inputResolver: InputResolver,
    private val echo: (Any?) -> Unit,
) {

    fun run(context: DefaultContext) : SolutionResult {
        val (day, year, runP1, runP2, sessionCookie) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day $day of $year is not implemented")
        val input = inputResolver.fetchAdventInput(day, year, sessionCookie)
        return solution.run(runP1, runP2, input)
    }

    fun run(context: ExampleContext) : SolutionResult {
        val (day, year, runP1, runP2) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day $day of $year is not implemented")
        val input = inputResolver.fetchExampleInput(day, year)
        return solution.run(runP1, runP2, input)
    }

    fun run(context: CustomContext) : SolutionResult {
        val (day, year, runP1, runP2, path) = context
        val solution = solutionSelector.select(day, year) ?: throw UsageError("day $day of $year is not implemented")
        val input = inputResolver.fetchCustomInput(day, year, path)
        return solution.run(runP1, runP2, input)
    }

    private fun Solution.run(runP1: Boolean, runP2: Boolean, input: SolutionInput) : SolutionResult {
        var part1: String? = null
        if (runP1) {
            val part1Duration = measureTime { part1 = part1(input) }
            echo("Part 1: $part1 ($part1Duration)")
        }

        if (part1?.isBlank() == true) {
            echo("Part 1 not implemented")
        }

        var part2: String? = null
        if (runP2) {
            val part2Duration = measureTime { part2 = part2(input) }
            echo("Part 2: $part2 ($part2Duration)")
        }

        if (part2?.isBlank() == true) {
            echo("Part 2 not implemented")
        }

        return SolutionResult(part1, part2)
    }
}
