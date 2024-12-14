package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day07 : Solver {

    fun List<Long>.canObtain(target: Long, part2: Boolean = false): Boolean {
        if (size == 1) return target == first()
        if (target % last() == 0L && subList(0, size - 1).canObtain(target / last(), part2)) return true
        if (target > last() && subList(0, size - 1).canObtain(target - last(), part2)) return true
        if (!part2) return false
        // Part 2 || operator
        val targetString = target.toString()
        val lastString = last().toString()
        if (targetString.length <= lastString.length) return false
        if (!targetString.endsWith(lastString)) return false
        val trimmedList = subList(0, size - 1)
        val trimmedTarget = targetString.substring(0, targetString.length - lastString.length).toLong()
        return trimmedList.canObtain(trimmedTarget, true)
    }

    override fun part1(input: List<String>): String {
        var total = 0L
        for (line in input) {
            val (left, right) = line.split(": ")
            val target = left.toLong()
            val numbers = right.split(' ').map { it.toLong() }
            if (numbers.canObtain(target)) {
                total += target
            }
        }
        return total.toString()
    }

    override fun part2(input: List<String>): String {
        var total = 0L
        for (line in input) {
            val (left, right) = line.split(": ")
            val target = left.toLong()
            val numbers = right.split(' ').map { it.toLong() }
            if (numbers.canObtain(target, part2 = true)) {
                total += target
            }
        }
        return total.toString()
    }
}
