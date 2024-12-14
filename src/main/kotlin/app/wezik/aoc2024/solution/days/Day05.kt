package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day05 : Solver {

    private data class Rule(val value: Int, val before: Int)
    private data class Update(val pages: List<Int>)

    private fun List<String>.parse(): Pair<List<Rule>, List<Update>> {
        val rules = mutableListOf<Rule>()
        val updates = mutableListOf<Update>()

        val splitLine = this.indexOf("")

        this.subList(0, splitLine).forEach { ruleInput ->
            val (a, b) = ruleInput.split("|")
            rules.add(Rule(a.toInt(), b.toInt()))
        }

        this.subList(splitLine + 1, this.size).forEach { updateInput ->
            val pages = updateInput.split(",").map { it.toInt() }
            updates.add(Update(pages))
        }

        return rules to updates
    }

    override fun part1(input: List<String>): String {
        val (rules, updates) = input.parse()
        return updates.filter { it.isValid(rules) }.map { it.pages[it.pages.size / 2] }.sum().toString()
    }

    private fun Update.isValid(rules: List<Rule>): Boolean {
        val applicableRules = rules.filter { it.before in this.pages && it.value in this.pages }
        val passed = mutableSetOf<Int>()
        for (page in this.pages) {
            val pageRules = applicableRules.filter { it.value == page }
            val isInvalid = pageRules.find { it.before in passed } != null
            if (isInvalid) return false
            passed.add(page)
        }
        return true
    }

    override fun part2(input: List<String>): String {
        val (rules, updates) = input.parse()
        return updates.filter { !it.isValid(rules) }.map { it.toFixed(rules).pages[it.pages.size / 2] }.sum().toString()
    }

    private fun Update.toFixed(rules: List<Rule>): Update {
        val leftover = this.pages.toMutableList()
        val applicableRules = rules.filter { it.before in this.pages && it.value in this.pages }
        val passed = mutableListOf<Int>()
        while (leftover.isNotEmpty()) {
            val nextPage = leftover.withIndex().find { page ->
                applicableRules.find { it.before == page.value && it.value !in passed } == null
            }
            if (nextPage == null) error("Page is null, input is incorrect")
            leftover.removeAt(nextPage.index)
            passed.add(nextPage.value)
        }
        return Update(passed)
    }
}
