package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import java.util.BitSet

object Day10 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        fun String.toInt2() = this.replace("#", "1").replace(".", "0").toInt(2)
        fun Set<Int>.toInt2(diagramSize: Int): Int {
            var mask = 0
            for (i in this) {
                mask = mask or (1 shl (diagramSize - 1 - i))
            }
            return mask
        }

        val diagrams = mutableListOf<Pair<String, List<Set<Int>>>>()

        for (i in 0 until input.lines.size) {
            val line = input.lines[i]
            val entries = line.split(" ").toMutableList()
            val desired = entries.removeFirst().replace("[", "").replace("]", "")

            entries.removeLast() // remove joltage requirements as they are not relevant to this problem

            diagrams += desired to entries.map { entry -> entry.replace("(", "").replace(")", "").split(",").map { value -> value.toInt() }.toSet() }
        }

        var sum = 0
        for ((diagram, buttons) in diagrams) {
            val buttons = buttons.map { it.toInt2(diagram.length) }
            val desired = diagram.toInt2()

            val visited = BooleanArray(1 shl diagram.length)
            val queue = ArrayDeque<Pair<Int, Int>>()

            queue.add(".".repeat(diagram.length).toInt2() to 0)

            outer@ while (queue.isNotEmpty()) {
                val (state, depth) = queue.removeFirst()

                if (visited[state]) continue
                visited[state] = true

                for (button in buttons) {
                    val flipped = state xor button
                    if (flipped xor desired == 0) {
                        sum += depth + 1
                        break@outer
                    }

                    queue.add(flipped to depth + 1)
                }
            }
        }

        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        return SolutionResult.NotImplemented("incomplete, current solution will run you out of memory despite heavy optimizations")
        // Basically a custom hash function, to go easy on GC
        fun IntArray.toInt(requirement: IntArray): Long {
            var code = 0L
            var base = 1L

            for (i in this.indices) {
                code += this[i].toLong() * base
                base *= (requirement[i] + 1).toLong()
            }
            return code
        }

        // A paged boolean array, since we are overflowing the maximum integer value for an index
        data class PagedBooleanArray(val size: Long) {
            val pageSize = 1 shl 26 // ~67M bits
            val pages = HashMap<Long, BitSet>()

            operator fun get(index: Long): Boolean {
                val page = index / pageSize
                val offset = (index % pageSize).toInt()
                return pages[page]?.get(offset) == true
            }

            operator fun set(index: Long, value: Boolean) {
                val page = index / pageSize
                val offset = (index % pageSize).toInt()
                pages.getOrPut(page) { BitSet(pageSize) }.apply { if (value) set(offset) else clear(offset) }
            }
        }

        val requirements = mutableListOf<Pair<IntArray, List<IntArray>>>()

        for (i in 0 until input.lines.size) {
            val line = input.lines[i]
            val entries = line.split(" ").toMutableList()
            entries.removeFirst() // remove indicators as they are not relevant to this problem

            val requirement = entries.removeLast().replace("{", "").replace("}", "").split(",").map { value -> value.toInt() }.toIntArray()
            requirements += requirement to entries.map { entry -> entry.replace("(", "").replace(")", "").split(",").map { value -> value.toInt() }.toIntArray() }
        }

        var sum = 0
        for ((requirementArr, buttons) in requirements) {
            val queue = ArrayDeque<Pair<IntArray, Int>>()
            val requirement = requirementArr.toInt(requirementArr)

            val totalStates = requirementArr.fold(1L) { acc, v -> acc * (v + 1).toLong() }
            val visited = PagedBooleanArray(totalStates)

            queue.add(IntArray(requirementArr.size) to 0)

            outer@ while (queue.isNotEmpty()) {
                val (state, depth) = queue.removeFirst()

                val encoded = state.toInt(requirementArr)
                if (visited[encoded]) continue
                visited[encoded] = true

                button@ for (button in buttons) {

                    val incremented = state.clone()
                    for (index in button) {
                        incremented[index] = incremented[index] + 1
                        if (requirementArr[index] < incremented[index]) continue@button
                    }

                    if (incremented.toInt(requirementArr) == requirement) {
                        sum += depth + 1
                        break@outer
                    }

                    // println("Adding solution branch at depth $depth")
                    queue.add(incremented to depth + 1)
                }
            }
        }

        return SolutionResult.Success(sum)
    }
}
