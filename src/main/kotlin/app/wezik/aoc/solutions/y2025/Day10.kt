package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult

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
        return SolutionResult.Success(0)
    }
}
