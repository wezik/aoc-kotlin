package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day15 : Solver {

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

    override fun part1(input: List<String>): String {
        val breakage = input.withIndex().first { it.value.isEmpty() }
        val input1 = input.subList(0, breakage.index)
        val input2 = input.subList(breakage.index + 1, input.size)
        val rows = input1.size
        val cols = input1.first().length
        var pos = 0 to 0
        val walls = List(rows) { BooleanArray(cols) }
        val boxes = List(rows) { BooleanArray(cols) }
        val moves = mutableListOf<String>()
        for (y in 0 until rows) {
            for (x in 0 until input[y].length) {
                when (input[y][x]) {
                    '@' -> pos = x to y
                    '#' -> walls[y][x] = true
                    'O' -> boxes[y][x] = true
                }
            }
        }
        for (y in 0 until input2.size) {
            moves.addAll(input2[y].split(""))
        }
//        fun printGrid() {
//            var str = ""
//            for (y in 0 until rows) {
//                for (x in 0 until cols) {
//                    if (pos.first == x && pos.second == y) {
//                        str += "@"
//                    } else if (boxes[y][x]) {
//                        str += "O"
//                    } else if (walls[y][x]) {
//                        str += "#"
//                    } else {
//                        str += "`"
//                    }
//                }
//                str += "\n"
//            }
//            println(str)
//        }
        while (moves.isNotEmpty()) {
            val dir = when (moves.removeFirst()) {
                "^" -> Pair(0, -1)
                "v" -> Pair(0, 1)
                ">" -> Pair(1, 0)
                "<" -> Pair(-1, 0)
                else -> Pair(0, 0)
            }
            if (dir == Pair(0, 0)) continue

            var (cx, cy) = pos + dir

            var hasSpace = false
            while (!hasSpace && !walls[cy][cx]) {
                if (!boxes[cy][cx]) hasSpace = true
                cx += dir.first
                cy += dir.second
            }
            if (!hasSpace) continue
            cx = pos.first + dir.first
            cy = pos.second + dir.second
            var wasEmpty = false
            var previous = false
            while (!walls[cy][cx] && !wasEmpty) {
                val temp = boxes[cy][cx]
                boxes[cy][cx] = previous
                previous = temp
                if (!previous) wasEmpty = true
                cx += dir.first
                cy += dir.second
            }
            pos = pos + dir
        }

        val result = boxes.withIndex().map { (y, row) ->

            row.withIndex().map {
                if (it.value) {
                    return@map (100 * y + it.index).toLong()
                } else {
                    return@map 0L
                }
            }.sum()
        }.sum()

        return result.toString()
    }

    override fun part2(input: List<String>) = ""

}
