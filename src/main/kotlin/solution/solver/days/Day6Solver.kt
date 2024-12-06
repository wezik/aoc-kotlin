package org.example.solution.days

import org.example.solution.solver.Solver

class Day6Solver : Solver {

    private data class Guard(var x: Int, var y: Int)

    // Boolean represents if the cell is blocked or not
    private data class Board(val grid: List<List<Boolean>>)

    private fun List<String>.parse(): Pair<Board, Guard> {
        val grid = ArrayList<List<Boolean>>()
        val guard = Guard(0, 0)
        forEachIndexed { y, line ->
            val row = ArrayList<Boolean>()
            grid.add(row)
            line.forEachIndexed { x, c ->
                when (c) {
                    '#' -> row.add(true)
                    '.' -> row.add(false)
                    '^' -> {
                        row.add(false)
                        guard.x = x
                        guard.y = y
                    }
                }
            }
        }
        return Board(grid) to guard
    }

    override fun part1(input: List<String>): Int {
        var (board, guard) = input.parse()
        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(guard.x to guard.y)
        while (true) {
            guard = guard.next(board) ?: break
            visited.add(guard.x to guard.y)
        }
        return visited.size
    }

    private interface Direction {
        fun next(guard: Guard): Guard
    }

    private object Up : Direction {
        override fun next(guard: Guard) = Guard(guard.x, guard.y - 1)
    }

    private object Down : Direction {
        override fun next(guard: Guard) = Guard(guard.x, guard.y + 1)
    }

    private object Left : Direction {
        override fun next(guard: Guard) = Guard(guard.x - 1, guard.y)
    }

    private object Right : Direction {
        override fun next(guard: Guard) = Guard(guard.x + 1, guard.y)
    }

    companion object {
        private var currentDirection: Direction = Up
    }

    private fun Guard.turnRight() {
        currentDirection = when (currentDirection) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
            else -> Up
        }
    }

    private fun Board.toString(guard: Guard, visited: Set<Pair<Int, Int>>): String {
        return grid.mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                return@mapIndexed when {
                    x == guard.x && y == guard.y -> "[^]"
                    visited.contains(x to y) -> "[o]"
                    cell -> "[#]"
                    else -> "[.]"
                }
            }.joinToString("")
        }.joinToString("\n")
    }

    private fun Guard.next(board: Board): Guard? {
        // Default move up if blocked turn 90 clockwise
        val next = currentDirection.next(this)

        // Early return if guard has left the board
        if (0 > next.y || board.grid.size <= next.y) return null
        if (0 > next.x || board.grid[next.y].size <= next.x) return null

        val isBlocked = board.grid[next.y][next.x]
        if (isBlocked) {
            // Turn right and return previous
            next.turnRight()
            return this
        }
        return next
    }

    override fun part2(input: List<String>): Int {
        return 0
    }

}