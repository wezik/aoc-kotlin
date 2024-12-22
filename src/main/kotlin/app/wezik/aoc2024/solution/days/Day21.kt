package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import arrow.core.memoize
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
        ));
        //@formatter:on

        // map of a button to its position, more of a convenience
        val positions = this.flatMapPositions()
        private fun flatMapPositions(): Map<Char, Pair<Int, Int>> {
            val positions = HashMap<Char, Pair<Int, Int>>()
            for (y in 0 until this.buttons.size) {
                for (x in 0 until this.buttons[y].size) {
                    val btn = this.buttons[y].getOrNull(x) ?: continue
                    positions[btn] = x to y
                }
            }
            return positions
        }
    }

    // pre-computed numeric keypad sequences
    private val numSequence = findAllSequences(Keypad.NUMERIC)

    // pre-computed directional keypad sequences
    private val dirSequence = findAllSequences(Keypad.DIRECTIONAL)

    // cache of the shortest length between two directional buttons
    private val dirCache = dirSequence.entries.map { (it.key to it.value.first().length.toLong()) }.toMap()

    // entry point!!! code is a mess, but it somehow reads from top to bottom
    override fun part1(input: List<String>) = common(input, 2)
    override fun part2(input: List<String>) = common(input, 25)
    private fun common(input: List<String>, depth: Int): String {
        var total = 0L
        solve = ::solveWorker.memoize() // reset cache for benchmarks
        for (line in input) {
            val inputs = solveNumpad(line, numSequence)
            val length = inputs.map { solve(it, depth) }.min()
            total += length * line.substring(0 until line.length - 1).toLong()
        }
        return total.toString()
    }

    // normal solve returning all possible sequences (still needed for numpad -> directional)
    private fun solveNumpad(code: String, sequences: Map<Pair<Char, Char>, List<String>>): List<String> {
        // append A to the code to include move from A to first position
        val possible = "A$code".zip(code).map { (a, b) -> sequences[a to b] ?: error("Wrong sequence") }
        return cartesianProduct(possible)
    }

    // calculating and storing only the length of the shortest possibles sequence
    // note: tried MemoizedDeepRecursiveFunction, but it's annoying to set up and achieves the same thing
    private var solve = ::solveWorker.memoize()
    private fun solveWorker(sequence: String, depth: Int): Long {
        if (depth == 1) return "A$sequence".zip(sequence).sumOf { (a, b) -> dirCache[a to b]!! }
        var length = 0L
        for ((a, b) in "A$sequence".zip(sequence)) {
            length += dirSequence[a to b]!!.map { solve(it, depth - 1) }.min()
        }
        return length
    }

    private fun findAllSequences(keypad: Keypad): Map<Pair<Char, Char>, List<String>> {
        val sequences = HashMap<Pair<Char, Char>, List<String>>()
        // find all possible sequences for matrix of positions
        for (source in keypad.positions.keys) {
            for (destination in keypad.positions.keys) {
                sequences[source to destination] = bfsFindSequences(source, destination, keypad)
            }
        }
        return sequences
    }

    // compute all possible sequences between two buttons with BFS
    private fun bfsFindSequences(src: Char, dest: Char, keypad: Keypad): List<String> {
        // if the same just confirm
        if (src == dest) return listOf("A")
        val possible = mutableListOf<String>()
        val queue = ArrayDeque(listOf(keypad.positions[src]!! to ""))
        var best = Int.MAX_VALUE
        bfs@ while (queue.isNotEmpty()) {
            val (pos, acc) = queue.removeFirst()
            // next X, Y, move
            for ((nx, ny, nm) in pos.neighbourCandidates()) {
                if (ny !in 0 until keypad.buttons.size || nx !in 0 until keypad.buttons[ny].size) continue
                val btn = keypad.buttons[ny].getOrNull(nx) ?: continue
                if (btn == dest) {
                    if (best < acc.length + 1) break@bfs
                    best = acc.length + 1
                    possible += acc + nm + 'A'
                } else {
                    queue += Pair(nx to ny, acc + nm)
                }
            }
        }
        return possible
    }

    private fun Pair<Int, Int>.neighbourCandidates(): List<Triple<Int, Int, Char>> {
        val (x, y) = this
        return listOf(Triple(x - 1, y, '<'), Triple(x + 1, y, '>'), Triple(x, y - 1, '^'), Triple(x, y + 1, 'v'))
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

}
