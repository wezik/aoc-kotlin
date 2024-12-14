package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import kotlin.collections.first

class Day09 : Solver {

    override fun part1(input: List<String>): String {
        // Parse
        var fileId = 0
        val disk = mutableListOf<Int>()
        for ((i, x) in input.first().withIndex()) {
            if (i % 2 != 0) repeat(x.digitToInt()) { disk.add(-1) }
            else {
                repeat(x.digitToInt()) { disk.add(fileId) }
                fileId++
            }
        }

        // Process
        val freeSpace = disk.withIndex().filter { it.value == -1 }.map { it.index }
        for (i in freeSpace) {
            while (disk.last() == -1) disk.removeLast()
            if (disk.size <= i) break
            disk[i] = disk.removeLast()
        }

        // Check sum
        return disk.withIndex().map { (i, x) -> (i * x).toLong() }.sum().toString()
    }

    override fun part2(input: List<String>): String {
        // Parse
        var fileId = 0
        var pos = 0
        val freeSpace = mutableListOf<Pair<Int, Int>>()
        val files = mutableMapOf<Int, Pair<Int, Int>>()
        for ((i, x) in input.first().withIndex()) {
            val x = x.digitToInt()
            if (i % 2 == 0) {
                files[fileId] = pos to x
                fileId++
            } else if (x != 0) {
                freeSpace.add(pos to x)
            }
            pos += x
        }

        // Process
        while (fileId > 0) {
            fileId -= 1
            val (pos, size) = files[fileId]!!
            for ((i, value) in freeSpace.withIndex()) {
                val (start, length) = value
                if (start >= pos) {
                    freeSpace.subList(i + 1, freeSpace.size).clear()
                    break
                }
                if (size <= length) {
                    files[fileId] = start to size
                    if (size == length) {
                        freeSpace.removeAt(i)
                    } else {
                        freeSpace[i] = start + size to length - size
                    }
                    break
                }
            }
        }

        // Check sum
        var checkSum = 0L
        for ((fileId, value) in files) {
            val (pos, size) = value
            for (x in (pos until pos + size)) {
                checkSum += fileId * x
            }
        }
        return checkSum.toString()
    }

}
