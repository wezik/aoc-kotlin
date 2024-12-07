package org.example.solution.solver.days

import org.example.solution.solver.Solver

private typealias Rules = Map<Int, Rule>
private typealias Rule = MutableSet<Int>

private typealias Updates = List<Update>
private typealias Update = List<Int>

class Day5Solver : Solver {

    private fun List<String>.parse(): Pair<Rules, Updates> {
        val rules = mutableMapOf<Int, Rule>()
        val updates = mutableListOf<Update>()

        val splitLine = this.indexOf("")

        this.subList(0, splitLine).forEach { ruleInput ->
            val (a, b) = ruleInput.split("|")
            val aRule = rules.getOrPut(a.toInt()) { HashSet<Int>() }
            aRule.add(b.toInt())
        }

        this.subList(splitLine + 1, this.size).forEach { updateInput ->
            val pages = updateInput.split(",").map { it.toInt() }
            updates.add(pages)
        }

        return rules to updates
    }

    override fun part1(input: List<String>): String {
        val (rules, updates) = input.parse()
        return updates
            .filter { it.isValid(rules) }
            .map { it.getMiddlePage() }
            .sum().toString()
    }

    private fun Update.isValid(rules: Rules): Boolean {
        // Look for first invalid page
        return withIndex().find { !isValidPage(it.index, rules) } == null
    }

    private fun Update.isValidPage(i: Int, rules: Rules): Boolean {
        // Rules are restrictions so on no rule it is valid
        val rule = rules[this[i]] ?: return true
        // No rule should be present before the page
        return this.subList(0, i).find { rule.contains(it) } == null
    }

    private fun Update.getMiddlePage() = this[this.size / 2]

    override fun part2(input: List<String>): String {
        val (rules, updates) = input.parse()
        return updates
            .filter { !it.isValid(rules) }
            .map { it.fix2(rules).getMiddlePage() }
            .sum().toString()
    }

    private fun Update.findCorrect(rules: Rules): Int? {
        // TODO This part is broken
        return withIndex().find {
            val rule = rules[this[it.index]] ?: return@find true
            this.subList(0, it.index).find { rule.contains(it) } == null
        }?.value
    }

    private fun Update.fix2(rules: Rules): Update {
        val newPages = ArrayList<Int>(size)
        val sourcePages = this.toMutableList()

        while (sourcePages.isNotEmpty()) {
            val nextPage = sourcePages.findCorrect(rules)
            if (nextPage != null) {
                newPages.add(nextPage)
                sourcePages.remove(nextPage)
            } else {
                throw IllegalStateException("Page is null")
            }
        }
        return newPages
    }

    // Boolean is true if the update was fixed
    private fun Update.fix(rules: Rules): Pair<Update, Boolean> {
        var newPages = ArrayList<Int>(size)
        val pageSource = this.reversed().toMutableList()
        var wasFixed = false

        // while pages filled and not the same as previous run!
        while (pageSource.isNotEmpty()) {
            val page = pageSource.find {

                // take the rules

                // If no rules it's valid
                val rule = rules[it] ?: return@find true
                // Trim unrelated rules to this update
                val trimmedRules = rule.filter { this.contains(it) }
                val rulesExhausted = trimmedRules.filter { !newPages.contains(it) }.isEmpty()
                if (!rulesExhausted) {
                    // Fix had to be done
                    wasFixed = true
                    return@find false
                }
                return@find true
            }
            if (page != null) {
                newPages.add(page)
                pageSource.remove(page)
            }
            if (page == null) {
                throw IllegalStateException("Page is null")
            }
        }

        val result = newPages.reversed()

        return newPages.reversed() to wasFixed
    }

}
