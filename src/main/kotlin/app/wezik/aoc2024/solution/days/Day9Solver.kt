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
        println(this.joinToString(separator = "") { it?.toString() ?: "." })
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

    private fun List<Int?>.organizeFreeSpacePart2(): List<Int?> {
        val organized = this.toMutableList()
        val uniqueStarts = this.findUnique()

        for (uniqueStart in uniqueStarts.reversed()) {
            val takenSize = organized.getSizeFrom(uniqueStart.index)
            for (i in 0 until uniqueStart.index) {
                val isFree = organized[i] == null
                if (!isFree) continue
                val freeSize = organized.getSizeFrom(i)
                // Can't reassign `i` to skip empty spaces so it iterates without a need for it...
                if (freeSize < takenSize) continue
                for (j in i until i + takenSize) {
                    val x = uniqueStart.index + (j - i)
                    organized[j] = organized[uniqueStart.index + (j - i)]
                    organized[uniqueStart.index + (j - i)] = null
                }
            }
        }

        return organized
    }

    private fun List<Int?>.getSizeFrom(index: Int): Int {
        val value = this[index]
        var size = 1
        var nextValue = this[index + size]
        while (value == nextValue) {
            size++
            if (index + size >= this.size) break
            nextValue = this[index + size]
        }
        return size
    }

    private fun List<Int?>.findUnique(): List<IndexedValue<Int?>> {
        val uniqueSet = mutableSetOf<Int>()
        return this.withIndex().filter {
            if (it.value != null && uniqueSet.contains(it.value).not()) {
                uniqueSet.add(it.value!!)
                return@filter true
            }
            return@filter false
        }
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
        val input = input.parse()
        val organized = input.organizeFreeSpacePart2()
        return organized.checkSum().toString()
    }

}
