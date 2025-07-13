package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day05 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val (constraints, updates) = parse(input)

        val result = updates
            .filter { isValid(it, constraints) }
            .sumOf { it[it.size / 2] }

        return Success(result)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val (constraints, updates) = parse(input)

        fun fix(update: List<Int>): List<Int> {
            val trimmedConstraints = constraints
                .filterKeys { it in update }
                .mapValues { it.value.filter { v -> v in update } }

            // NOTE: we can just sort by the amount of constraints (trimmed to those present in the update)
            // it works due to trimming indirectly telling us the 'n' of entries required to satisfy the constraint (therefore order)
            return update.sortedByDescending { trimmedConstraints[it]?.size ?: 0 }
        }

        val result = updates
            .filter { !isValid(it, constraints) }
            .map { fix(it) }
            .sumOf { it[it.size / 2] }

        return Success(result)
    }

    private data class ParseOutput(
        val constraints: Map<Int, MutableList<Int>>,
        val updates: List<List<Int>>
    )

    private fun parse(input: SolutionInput): ParseOutput {
        val (constraintData, updateData) = input.content.split("\n\n")

        val constraints = mutableMapOf<Int, MutableList<Int>>()
        for (data in constraintData.lines()) {
            if (data.isBlank()) continue
            val (x, y) = data.split('|')
            constraints.getOrPut(x.toInt()) { mutableListOf() }.add(y.toInt())
        }

        val updates = mutableListOf<List<Int>>()
        for (data in updateData.lines()) {
            if (data.isBlank()) continue
            updates += data.split(',').map { it.toInt() }
        }

        return ParseOutput(constraints, updates)
    }

    fun isValid(update: List<Int>, constraints: Map<Int, MutableList<Int>>): Boolean {
        val seen = mutableSetOf<Int>()

        // reversing it makes checking for "before" values easier
        val reversed = update.reversed()

        for (v in reversed) {
            val constraint = constraints[v] ?: emptyList()
            for (c in constraint) {
                if (c in seen) continue
                if (c in reversed) return false
            }
            seen += v
        }

        return true
    }
}
