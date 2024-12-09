package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day9Solver : Solver {

    private fun List<String>.parse(): MutableList<Int?> {
        val entries = mutableListOf<Int?>()
        var memoryIndex = 0
        for (i in 0 until this.first().length) {
            val value = this.first()[i].digitToInt()
            if (i % 2 == 0) {
                entries.addAll((0 until value).map { memoryIndex })
                memoryIndex += 1
            } else {
                entries.addAll((0 until value).map { null })
            }
        }
        return entries
    }

    private fun List<Int?>.debugPrint() {
        println(this.joinToString(separator = "") { "[${it?.toString() ?: "."}]" })
    }

    private fun List<Int?>.organizeFreeSpace(): List<Int?> {
        val organized = this.toMutableList()

        var firstFreeSpaceIndex = organized.withIndex().find { it.value == null }?.index
        var lastTakenSpaceIndex = organized.withIndex().findLast { it.value != null }?.index

        while (firstFreeSpaceIndex!! < lastTakenSpaceIndex!!) {
            print("\rFree space index: $firstFreeSpaceIndex / last taken space index: $lastTakenSpaceIndex")
            organized.withIndex().find { it.value == null }?.let {
                organized[it.index] = organized[lastTakenSpaceIndex]
                organized[lastTakenSpaceIndex] = null
            }
            firstFreeSpaceIndex = organized.withIndex().find { it.value == null }?.index
            lastTakenSpaceIndex = organized.withIndex().findLast { it.value != null }?.index
        }
        println()
        organized.debugPrint()

        return organized
    }


    private fun List<Int?>.checkSum(): Long {
        return this.withIndex()
            .filter { it.value != null }
            .map { (it.index * it.value!!).toLong() }
            .sum()
    }

    override fun part1(input: List<String>): String {
        val input = input.parse()

        val organized = input.organizeFreeSpace()
        val sum = organized.checkSum()
        println("Sum: $sum")

        return sum.toString()
    }

    override fun part2(input: List<String>): String {
        return ""
    }

}
