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

    private companion object {
        val directions = mapOf(
            '^' to Pair(0, -1),
            'v' to Pair(0, 1),
            '>' to Pair(1, 0),
            '<' to Pair(-1, 0),
        )

        val expansion = mapOf(
            "#" to "##",
            "O" to "[]",
            "." to "..",
            "@" to "@.",
        )
    }

    override fun part2(input: List<String>): String {
        val splitIndex = input.withIndex().first { it.value.isEmpty() }.index
        val grid = input.subList(0, splitIndex).map { line ->
            var str = line
            expansion.forEach { (k, v) -> str = str.replace(k, v) }
            str.toCharArray().toMutableList()
        }
        val moves = input.subList(splitIndex + 1, input.size).flatMap { it.toCharArray().toList() }

        var pos = -1 to -1
        for (y in 0 until grid.size) {
            if (pos.first != -1) break
            for (x in 0 until grid.first().size) {
                if (grid[y][x] == '@') {
                    pos = x to y
                    break
                }
            }
        }

        fun printGrid() {
            var str = buildString {
                for (y in 0 until grid.size) {
                    val str = (grid[y].joinToString("")+"\n").toCharArray().toMutableList()
                    if (y == pos.second) {
                        str[pos.first] = '*'
                    }
                    append(str.joinToString(""))
                }
            }
            println(str)
        }

//        printGrid()
        for (move in moves) {
            val dir = directions[move] ?: continue
            val stack = mutableListOf<Pair<Int, Int>>()
            val trackList = mutableMapOf((pos to grid[pos.second][pos.first]))
            stack.add(pos)
            var canMove = true
            while (stack.isNotEmpty()) {
                val (cx, cy) = stack.removeFirst()
                trackList[(cx to cy)] = grid[cy][cx]
                val (nx, ny) = (cx to cy) + dir
                if ((nx to ny) in stack) continue
                when (grid[ny][nx]) {
                    '[' -> stack.addAll(listOf(nx to ny, nx + 1 to ny))
                    ']' -> stack.addAll(listOf(nx to ny, nx - 1 to ny))
                    '.' -> continue
                    '#' -> {
                        canMove = false
                        break
                    }
                    else -> continue
                }
            }

            if (!canMove) {
//                printGrid()
//                println(move)
//                println(trackList)
//                Thread.sleep(200)
                continue
            }

            trackList.keys.forEach { (tx, ty) -> grid[ty][tx] = '.' }

            for ((k, v) in trackList) {
                val (tx, ty) = k
                val (nx, ny) = (tx to ty) + dir
                grid[ny][nx] = v
            }
            pos = pos + dir
//            printGrid()
//            println(trackList)
//            println(move)
//            Thread.sleep(200)
        }

        return grid.withIndex().map { (y, row) ->
            row .withIndex()
                .map { (x, value) -> if (value == '[') (100 * y + x).toLong() else 0L }
                .sum()
        }.sum().toString()
    }

}
