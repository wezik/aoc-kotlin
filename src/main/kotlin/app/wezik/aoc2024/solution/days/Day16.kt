package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import java.util.PriorityQueue

class Day16 : Solver {

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

    override fun part1(input: List<String>): String {
        val grid = List(input.size) { BooleanArray(input.first().length) }
        // start and end seem to be the same on every input
        var start = 1 to input.size - 2
        var end = input.first().length - 2 to 1
        for (row in 0 until input.size) {
            for (col in 0 until input[row].length) {
                if (input[row][col] != '#') grid[row][col] = true
            }
        }

        data class Foo(val cost: Int, val position: Pair<Int, Int>, val direction: Pair<Int, Int>) {
            override fun toString(): String {
                return "Foo(Cost: $cost, Position: $position, Direction: $direction)"
            }
        }

        val queue = PriorityQueue<Foo>(compareBy { it.cost })
        queue.add(Foo(0, start, (1 to 0)))
        val seen = mutableSetOf(start to (1 to 0))

        fun printBoard(str: String) {
            val str = buildString {
                val foo = mutableListOf<String>()
                for (y in 0 until grid.size) {
                    for (x in 0 until grid.first().size) {
                        if (seen.find { it.first == x to y } != null) {
                            append("██")
                            seen.filter { it.first == x to y }
                                .forEach { foo.add("Seen at ${it.first} with direction ${it.second}") }
                        } else if (grid[y][x]) {
                            append("  ")
                        } else {
                            append("[]")
                        }
                    }
                    append("\n")
                }
                append(str)
            }
            println("\u001B[1;1H\u001B[2J$str")
            Thread.sleep(20)
        }

        // Move to the top right corner of the terminal
//        println("\\u001B[1;1H")

        while (queue.isNotEmpty()) {
            val foo = queue.poll()
            seen.add(foo.position to foo.direction)
            if (foo.position == end) return foo.cost.toString()

//            val str = buildString {
//                append("Pulled from queue: ${foo}\n")
//                append("Next in queue: ${queue.peek()}\n")
//                append("Queue contains (first 10 elements):\n")
//                if (queue.size > 10) {
//                    queue.toList().subList(0, 10).forEach { append("${it}\n") }
//                    append("... and ${queue.size - 10} more\n")
//                } else {
//                    queue.toList().forEach { append("${it}\n") }
//                }
//            }
//            printBoard(str)

            // processing loop
            val (dx, dy) = foo.direction
            val ahead = Foo(foo.cost + 1, foo.position + (dx to dy), dx to dy)
            val right = Foo(foo.cost + 1000, foo.position, -dy to dx)
            val left = Foo(foo.cost + 1000, foo.position, dy to -dx)
            val candidates = listOf(ahead, right, left)
            for (next in candidates) {
                val (nx, ny) = next.position
                if (!grid[ny][nx]) continue
                if (seen.contains(next.position to next.direction)) continue
                queue.add(next)
            }
        }
        return ""
    }

    override fun part2(input: List<String>): String {
        val grid = List(input.size) { BooleanArray(input.first().length) }
        // start and end seem to be the same on every input
        var start = 1 to input.size - 2
        var end = input.first().length - 2 to 1
        for (row in 0 until input.size) {
            for (col in 0 until input[row].length) {
                if (input[row][col] != '#') grid[row][col] = true
            }
        }

        data class Foo(val cost: Int, val position: Pair<Int, Int>, val direction: Pair<Int, Int>) {
            override fun toString(): String {
                return "Foo(Cost: $cost, Position: $position, Direction: $direction)"
            }
        }

        val queue = PriorityQueue<Foo>(compareBy { it.cost })
        queue.add(Foo(0, start, (1 to 0)))
        val lowestCost = mutableMapOf((start to (1 to 0)) to 0)
        val backtrack = HashMap<Foo, MutableSet<Foo>?>()
        var bestCost = Int.MAX_VALUE
        val endStates = mutableSetOf<Foo>()

//        fun printBoard(str: String) {
//            val str = buildString {
//                val foo = mutableListOf<String>()
//                for (y in 0 until grid.size) {
//                    for (x in 0 until grid.first().size) {
//                        if (lowestCost.find { it.first == x to y } != null) {
//                            append("██")
//                            lowestCost.filter { it.first == x to y }
//                                .forEach { foo.add("Seen at ${it.first} with direction ${it.second}") }
//                        } else if (grid[y][x]) {
//                            append("  ")
//                        } else {
//                            append("[]")
//                        }
//                    }
//                    append("\n")
//                }
//                append(str)
//            }
//            println("\u001B[1;1H\u001B[2J$str")
//            Thread.sleep(20)
//        }

        // Move to the top right corner of the terminal
//        println("\\u001B[1;1H")

        while (queue.isNotEmpty()) {
            val foo = queue.poll()
            if (foo.cost > (lowestCost[foo.position to foo.direction] ?: Int.MAX_VALUE)) continue
            if (foo.position == end) {
                if (foo.cost > bestCost) break
                bestCost = foo.cost
                endStates.add(foo)
            }

//            val str = buildString {
//                append("Pulled from queue: ${foo}\n")
//                append("Next in queue: ${queue.peek()}\n")
//                append("Queue contains (first 10 elements):\n")
//                if (queue.size > 10) {
//                    queue.toList().subList(0, 10).forEach { append("${it}\n") }
//                    append("... and ${queue.size - 10} more\n")
//                } else {
//                    queue.toList().forEach { append("${it}\n") }
//                }
//            }
//            printBoard(str)

            // processing loop
            val (dx, dy) = foo.direction
            val ahead = Foo(foo.cost + 1, foo.position + (dx to dy), dx to dy)
            val right = Foo(foo.cost + 1000, foo.position, -dy to dx)
            val left = Foo(foo.cost + 1000, foo.position, dy to -dx)
            val candidates = listOf(ahead, right, left)
            for (next in candidates) {
                val (nx, ny) = next.position
                if (!grid[ny][nx]) continue
                val lowest = lowestCost[(nx to ny) to next.direction] ?: Int.MAX_VALUE
                if (next.cost > lowest) continue
                if (next.cost < lowest) {
                    backtrack[next] = mutableSetOf()
                    lowestCost[next.position to next.direction] = next.cost
                }
                backtrack[next]!!.add(foo)
                queue.add(next)
            }
        }

        val states = ArrayDeque<Foo>()
        states.addAll(endStates)
        val seen = HashSet<Foo>()
        seen.addAll(endStates)

        while (states.isNotEmpty()) {
            val foo = states.removeFirst()
            val origins = backtrack[foo] ?: emptySet()
            println("$foo -> $origins")
            for (origin in origins) {
                if (origin in seen) continue
                seen.add(origin)
                states.add(origin)
            }
        }

        println(endStates)

        val set = seen.map { it.position }.toSet()
        val str = buildString {
            for (y in 0 until grid.size) {
                for (x in 0 until grid.first().size) {
                    if (set.contains(x to y)) {
                        append("██")
                    } else {
                        append("  ")
                    }
                }
                append("\n")
            }
        }
        println(str)
        println(set)

        return set.size.toString()
    }

}
