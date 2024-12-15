package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day15 : Solver {

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

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

    override fun part1(input: List<String>): String {
        // parse
        val splitIndex = input.withIndex().first { it.value.isEmpty() }.index
        val grid = input.subList(0, splitIndex).map { line -> line.toMutableList() }
        val moves = input.subList(splitIndex + 1, input.size).flatMap { it.toList() }

        // find the robot
        var robot = grid.withIndex().first { it.value.contains('@') }.let { (y, row) -> row.indexOf('@') to y }

        // run the robot
        for (move in moves) {
            val dir = directions[move] ?: continue
            var canMove = true

            // stack of (x, y) positions
            val stack = mutableListOf<Pair<Int, Int>>()
            // map of tracked (x, y) positions
            val trackMap = mutableMapOf(robot to grid[robot.second][robot.first])

            stack.add(robot)
            while (stack.isNotEmpty() && canMove) {

                // current = cx, cy
                val (cx, cy) = stack.removeFirst()
                trackMap[cx to cy] = grid[cy][cx]

                // next = nx, ny
                val (nx, ny) = (cx to cy) + dir

                when (grid[ny][nx]) {
                    'O' -> stack.add(nx to ny)
                    '#' -> canMove = false
                }

            }

            // Early return, didn't move
            if (!canMove) continue

            // Update the grid
            robot = robot + dir
            trackMap.keys.forEach { (tx, ty) -> grid[ty][tx] = '.' }
            trackMap.map { (pos, value) -> pos + dir to value }
                .forEach { (next, value) -> grid[next.second][next.first] = value }
        }

        // output
        var coordinatesSum = 0L
        for ((y, row) in grid.withIndex()) {
            for ((x, value) in row.withIndex()) {
                if (value == 'O') coordinatesSum += (100 * y + x).toLong()
            }
        }
        return coordinatesSum.toString()
    }

    override fun part2(input: List<String>): String {
        // parse
        val splitIndex = input.withIndex().first { it.value.isEmpty() }.index
        val grid = input.subList(0, splitIndex).map { line ->
            expansion.entries.fold(line) { updatedLine, (key, value) ->
                updatedLine.replace(key, value)
            }.toMutableList()
        }
        val moves = input.subList(splitIndex + 1, input.size).flatMap { it.toList() }

        // find the robot
        var robot = grid.withIndex().first { it.value.contains('@') }.let { (y, row) -> row.indexOf('@') to y }

        // run the robot
        for (move in moves) {
            val dir = directions[move] ?: continue
            var canMove = true

            // stack of (x, y) positions
            val stack = mutableListOf<Pair<Int, Int>>()
            // map of tracked (x, y) positions
            val trackMap = mutableMapOf(robot to grid[robot.second][robot.first])

            stack.add(robot)
            while (stack.isNotEmpty() && canMove) {

                // current = cx, cy
                val (cx, cy) = stack.removeFirst()
                trackMap[cx to cy] = grid[cy][cx]

                // next = nx, ny
                val (nx, ny) = (cx to cy) + dir
                if (nx to ny in stack) continue

                when (grid[ny][nx]) {
                    '[' -> stack.addAll(listOf(nx to ny, nx + 1 to ny))
                    ']' -> stack.addAll(listOf(nx to ny, nx - 1 to ny))
                    '#' -> canMove = false
                }

            }

            // Early return, didn't move
            if (!canMove) continue

            // Update the grid
            robot = robot + dir
            trackMap.keys.forEach { (tx, ty) -> grid[ty][tx] = '.' }
            trackMap.map { (pos, value) -> pos + dir to value }
                .forEach { (next, value) -> grid[next.second][next.first] = value }
        }

        // output
        var coordinatesSum = 0L
        for ((y, row) in grid.withIndex()) {
            for ((x, value) in row.withIndex()) {
                if (value == '[') coordinatesSum += (100 * y + x).toLong()
            }
        }
        return coordinatesSum.toString()
    }

}
