package app.wezik.aoc.domain

import com.github.ajalt.clikt.core.UsageError

data class SolutionRunContext(
    val day: Day,
    val year: Year,
    val runMode: RunMode = RunMode.BOTH,
    val testRun: Boolean = false,
)

enum class RunMode { PART1, PART2, BOTH }

class SolutionRunner(
    private val solutionSelector: SolutionSelector,
    private val inputClient: InputClient,
    private val echo: (Any?) -> Unit,
) {

    fun run(context: SolutionRunContext) {
        val solution = solutionSelector.select(context.day, context.year)
            ?: throw UsageError("day ${context.day.value} of ${context.year.value} is not implemented")

        val file = inputClient.load(context.day, context.year).getOrElse { e -> 
            throw UsageError("failed to load input - ${e.message}")
        }

        val input = SolutionInput(file = file, isTestRun = context.testRun)

        if (context.runMode == RunMode.BOTH || context.runMode == RunMode.PART1) {
            echoRun("Part 1") { solution.part1(input) }
        }

        if (context.runMode == RunMode.BOTH || context.runMode == RunMode.PART2) {
            echoRun("Part 2") { solution.part2(input) }
        }
    }

    private fun echoRun(prefix: String, block: () -> SolutionResult) {
        val (result, duration) = timedRun(block)
        result.fold(
            onSuccess = { output ->
                when (output) {
                    is SolutionResult.Success -> echo("$prefix: ${output.output} ($duration)")
                    is SolutionResult.NotImplemented -> echo("$prefix: not implemented ${output.reason?.let { "reason: $it" }}")
                }
            },
            onFailure = { 
                error -> echo("$prefix failed with error: ${error.message} ($duration)").also { error.printStackTrace() }
            }
        )
    }
}
