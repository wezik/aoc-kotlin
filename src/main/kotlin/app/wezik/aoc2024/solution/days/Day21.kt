package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import kotlin.collections.ArrayDeque


class Day21 : Solver {

    private enum class Keypad(val buttons: List<List<Char?>>) {
        //@formatter:off
        NUMERIC(listOf(
            listOf('7', '8', '9'),
            listOf('4', '5', '6'),
            listOf('1', '2', '3'),
            listOf(null, '0', 'A'),
        )),
        DIRECTIONAL(listOf(
            listOf(null, '^', 'A'),
            listOf('<', 'v', '>'),
        ))
        //@formatter:on
    }

    private fun List<List<Char?>>.toCordsMap(): Map<Char, Pair<Int, Int>> {
        val result = HashMap<Char, Pair<Int, Int>>()
        for (y in 0 until this.size) {
            for (x in 0 until this[y].size) {
                val button = this[y].getOrNull(x) ?: continue
                result[button] = x to y
            }
        }
        return result
    }

    private fun Pair<Int, Int>.neighbourCandidates(): List<Triple<Int, Int, Char>> {
        val (x, y) = this
        return listOf(
            Triple(x - 1, y, '<'),
            Triple(x + 1, y, '>'),
            Triple(x, y - 1, '^'),
            Triple(x, y + 1, 'v'),
        )
    }

    private fun Map<Char, Pair<Int, Int>>.findAllSequences(keypad: List<List<Char?>>): Map<Pair<Char, Char>, List<String>> {
        // key: origin, destination, value: list of possible sequences
        val sequences = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (origin in this.keys) {
            for (destination in this.keys) {
                // If the same just enter input
                if (origin == destination) {
                    sequences[origin to destination] = listOf("A")
                    continue
                }
                // Compute all possible sequences
                val possibilities = mutableListOf<String>()
                // BFS, queue with cords and acc sequence
                val queue = ArrayDeque(listOf(this[origin]!! to ""))
                var best = Int.MAX_VALUE
                bfs@ while (queue.isNotEmpty()) {
                    val (cords, acc) = queue.removeFirst()
                    // neighbour (x, y, move)
                    for ((nx, ny, nm) in cords.neighbourCandidates()) {
                        if (ny !in 0 until keypad.size || nx !in 0 until keypad[ny].size) continue
                        val button = keypad[ny].getOrNull(nx) ?: continue
                        if (button == destination) {
                            if (best < acc.length + 1) break@bfs
                            best = acc.length + 1
                            possibilities.add((acc + nm + 'A').toString())
                        } else {
                            queue.add(Pair(nx to ny, (acc + nm).toString()))
                        }
                    }
                }
                sequences[origin to destination] = possibilities
            }
        }
        return sequences
    }


    private fun Map<Pair<Char, Char>, List<String>>.computePaths(code: String): List<String> {
        val steps = mutableListOf<List<String>>()
        var previous = 'A'
        for (c in code) {
            steps.add(this[previous to c]!!)
            previous = c
        }
        return cartesianProduct(steps)
    }

    private fun cartesianProduct(list: List<List<String>>): List<String> {
        if (list.isEmpty()) return listOf("")
        val head = list.first()
        val tail = cartesianProduct(list.drop(1))
        val result = head.flatMap { head ->
            tail.map { head + it }
        }
        return result
    }

    private fun solveCode(code: String, keypad: List<List<Char?>>): List<String> {
        // I hate this obfuscation but it helps to separate thoughts for now
        val cordsMap = keypad.toCordsMap()
        val sequences = cordsMap.findAllSequences(keypad)
        val paths = sequences.computePaths(code)
        return paths
    }

    override fun part1(input: List<String>): String {
        var total = 0L
        for (line in input) {
            // Robot 1
            val robot1 = solveCode(line, Keypad.NUMERIC.buttons)

            var next = robot1
            repeat(2) {
                val possibleNext = mutableListOf<String>()
                for (seq in next) {
                    possibleNext += solveCode(seq, Keypad.DIRECTIONAL.buttons)
                }
                val minLength = possibleNext.minOf { it.length }
                next = possibleNext.filter { it.length == minLength }
            }
            val length = next.first().length
            val complexity = length * line.substring(0 until line.length - 1).toLong()
            total += complexity
            println("Solved $line")
        }

        return total.toString()
    }

    override fun part2(input: List<String>) = ""

}
