package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day6Solver : Solver {

    private data class Guard(var x: Int, var y: Int)

    // Boolean represents if the cell is blocked or not
    private data class Board(val grid: List<List<Boolean>>) {
        val width = grid.first().size
        val height = grid.size
    }

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

    override fun part1(input: List<String>): String {
        var (board, guard) = input.parse()
        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(guard.x to guard.y)
        while (true) {
            guard = guard.next(board) ?: break
            visited.add(guard.x to guard.y)
        }
        return visited.size.toString()
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

    private fun Board.toString(guard: Guard, visited: Set<PosWrapper>): String {
        return grid.mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                return@mapIndexed when {
                    x == guard.x && y == guard.y -> "[^]"
                    visited.find { wrapper ->
                        if (wrapper.pos.first == x && wrapper.pos.second == y) {
                            return@find true
                        }
                        return@find false
                    } != null -> "[o]"

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

    private data class PosWrapper(val pos: Pair<Int, Int>, val direction: Direction) {
        override fun toString(): String {
            return "$pos ${direction::class.simpleName}"
        }
    }

    override fun part2(input: List<String>): String {
        currentDirection = Up
        var (board, guard) = input.parse()
        val visited = mutableSetOf<PosWrapper>()
        visited.add(PosWrapper(guard.x to guard.y, currentDirection))
        while (true) {
            guard = guard.next(board) ?: break
            visited.add(PosWrapper(guard.x to guard.y, currentDirection))
        }


        // Real part 2
        return visited.map { if (it.willLoop(board)) 1 else 0 }.sum().toString()
    }

    private fun PosWrapper.willLoop(board: Board): Boolean {
        currentDirection = direction

        // copy and add obstacle
        val next = direction.next(Guard(pos.first, pos.second))
        val boardWithObstacle = board.copy(grid = board.grid.mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                if (x == next.x && y == next.y) {
                    return@mapIndexed true
                }
                return@mapIndexed cell
            }
        })

        var guard = Guard(pos.first, pos.second)
        val visited = mutableSetOf<PosWrapper>()
        visited.add(PosWrapper(guard.x to guard.y, currentDirection))
        while (true) {
            guard = guard.next(boardWithObstacle) ?: break
            if (visited.contains(PosWrapper(guard.x to guard.y, currentDirection))) {
                return true
            }
            visited.add(PosWrapper(guard.x to guard.y, currentDirection))
        }
        return false
    }
}