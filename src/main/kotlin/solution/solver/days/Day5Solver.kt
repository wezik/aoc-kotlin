package org.example.solution.solver.days

import org.example.solution.solver.Result
import org.example.solution.solver.Solver
import org.example.solution.time
import org.example.solution.toNanoDuration

// alias
private typealias Rules = Map<Int, Rule>
private typealias Updates = List<Update>

private data class Rule(val before: MutableSet<Int>, val after: MutableSet<Int>)
private data class Update(val pages: List<Int>)

class Day5Solver : Solver {

    override fun solve(input: List<String>): Pair<Result, Result> {
        var part1Sum = 0
        val part1Time = time {
            part1Sum = part1(input)
        }.toNanoDuration()
        val part1Result = Result(part1Sum.toString(), part1Time)

        var part2Sum = 0
        val part2Time = time {
            part2Sum = part2(input)
        }.toNanoDuration()
        val part2Result = Result(part2Sum.toString(), part2Time)

        return part1Result to part2Result
    }

    private fun List<String>.split(): Pair<Rules, Updates> {
        val rules = mutableMapOf<Int, Rule>()
        val updates = mutableListOf<Update>()

        forEach { line ->
            if (line.contains("|")) {
                // Should be of pattern 'A|B' otherwise the input is incorrect
                val (a, b) = line.split("|")
                val aRule = rules.getOrPut(a.toInt()) { Rule(HashSet<Int>(), HashSet<Int>()) }
                val bRule = rules.getOrPut(b.toInt()) { Rule(HashSet<Int>(), HashSet<Int>()) }
                aRule.before.add(b.toInt())
                bRule.after.add(a.toInt())
            } else if (line.isNotBlank()) {
                val pages = line.split(",").map { it.toInt() }
                updates.add(Update(pages))
            }
        }
        return rules to updates
    }

    private fun part1(input: List<String>): Int {
        var sum = 0
        val (rules, updates) = input.split()
        for (update in updates) {
            if (rules.isUpdateValid(update)) {
                sum += update.pages[update.pages.size / 2]
            }
        }
        return sum
    }

    private fun Rules.isUpdateValid(update: Update): Boolean {
        val werePresent = HashSet<Int>()
        update.pages.reversed().forEachIndexed { index, page ->
            // If rule is null, then the page is not in the rules therefore it is allowed
            val rule = get(page) ?: return@forEachIndexed
            werePresent.add(page)

            // Trim rule for the pages that are present in the update
            val rulesTrimmed = rule.before.filter { update.pages.contains(it) }
            if (!werePresent.containsAll(rulesTrimmed)) {
                return false
            }
        }
        return true
    }

    private fun part2(input: List<String>): Int {
        return 0
    }

}
