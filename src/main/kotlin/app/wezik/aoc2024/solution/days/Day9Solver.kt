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
        val (listOfFreeSpaces, listOfTakenSpaces) = organized.withIndex().partition { it.value == null }.let {
            Pair(it.first.toMutableList(), it.second.toMutableList())
        }

        var firstFreeSpaceIndex = listOfFreeSpaces.removeFirst().index
        var lastTakenSpaceIndex = listOfTakenSpaces.removeLast().index

        while (firstFreeSpaceIndex < lastTakenSpaceIndex) {
            organized.withIndex().find { it.value == null }?.let {
                organized[it.index] = organized[lastTakenSpaceIndex]
                organized[lastTakenSpaceIndex] = null
            }
            firstFreeSpaceIndex = listOfFreeSpaces.removeFirst().index
            lastTakenSpaceIndex = listOfTakenSpaces.removeLast().index
        }

        return organized
    }


    private fun List<Int?>.checkSum(): Long {
        return this.withIndex().filter { it.value != null }.map { (it.index * it.value!!).toLong() }.sum()
    }

    override fun part1(input: List<String>): String {
        val input = input.parse()

        val organized = input.organizeFreeSpace()
        return organized.checkSum().toString()
    }

    override fun part2(input: List<String>): String {
        return ""
    }

}
