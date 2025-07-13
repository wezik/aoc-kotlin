package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

// NOTE: I take pride in making this a non-regex solution, idk who asked but I did
object Day03 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        // find all mul(<any>) candidates, dropping the first one since it is not viable
        val candidates = input.content.split("mul(").drop(1).map { it.substringBefore(')') }
        val muls = mutableListOf<Pair<Int, Int>>()

        for (candidate in candidates) {
            val segments = candidate.split(',')
            if (segments.size != 2) continue

            runCatching { 
                val (a, b) = segments.map { it.toInt() }
                muls += a to b
            }
        }

        val result = muls.map { (a, b) -> a * b }.sum()
        return Success(result)
    }

    // NOTE: state should persist between lines
    override fun part2(input: SolutionInput): SolutionResult {
        val allowBlock = input.content.split("do()")
        val muls = mutableListOf<Pair<Int, Int>>()

        for (block in allowBlock) {
            // trim to nearest don't() and map mul(<any>) again same as part1
            val candidates = block.substringBefore("don't()").split("mul(").drop(1).map { it.substringBefore(')') }

            for (candidate in candidates) {
                val segments = candidate.split(',')
                if (segments.size != 2) continue

                runCatching { 
                    val (a, b) = segments.map { it.toInt() }
                    muls += a to b
                }
            }
        }

        val result = muls.map { (a, b) -> a * b }.sum()
        return Success(result)
    }
}
