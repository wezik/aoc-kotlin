package org.example.solution.solver.days

import org.example.solution.solver.Solver

class Day6Solverv2 : Solver {

    private data class Vector2D(var x: Int, var y: Int)

    private data class Context(
        val width: Int,
        val height: Int,
        val guardPosition: Pos,
        val blockages: Set<Pos>,
        val visited: MutableSet<Pos> = mutableSetOf()
    )

    private fun Context.isInBounds() = guardPosition.x in 0 until width && guardPosition.y in 0 until height

    private data class Pos(var x: Int, var y: Int, var direction: Direction = Direction.UP)

    private enum class Direction { UP, RIGHT, LEFT, DOWN, }

    private fun Direction.getTurn() = when (this) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }

    private fun List<String>.parse(): Context {
        var guardPosition = Pos(0, 0)
        val blockages = mutableSetOf<Pos>()
        forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                when (cell) {
                    '^' -> guardPosition = Pos(x = x, y = y)
                    '#' -> blockages.add(Pos(x = x, y = y))
                }
            }
        }
        return Context(width = this.first().length, height = this.size, guardPosition, blockages)
    }

    override fun part1(input: List<String>): String {
        val context = input.parse()

        while (context.isInBounds()) {
            context.runSimulationTillBounce()
        }

        // Override direction to UP to the visited fields and remove repetitions
        // - 1 due to last move leaving the board
        return (context.visited.map { it.direction = Direction.UP }.toMutableSet().count() - 1).toString()
    }

    private fun Context.runSimulationTillBounce() {
        // Find elements in the direction of the guard
        var nextBlockade: Pos? = null
        val oob = runCatching { nextBlockade = getBlockade() }.isFailure
        if (oob) {
            when (guardPosition.direction) {
                // Essentially forces him to leave because there are no obstacles to bounce
                Direction.UP -> moveGuardY(-1)
                Direction.DOWN -> moveGuardY(height + 1)
                Direction.LEFT -> moveGuardX(-1)
                Direction.RIGHT -> moveGuardX(width + 1)
            }
            return
        }
        when(guardPosition.direction) {
            // Offset by 1 so guard stands just before the blockade
            Direction.UP -> moveGuardY(nextBlockade!!.y + 1)
            Direction.DOWN -> moveGuardY(nextBlockade!!.y - 1)
            Direction.LEFT -> moveGuardX(nextBlockade!!.x + 1)
            Direction.RIGHT -> moveGuardX(nextBlockade!!.x - 1)
        }
        guardPosition.direction = guardPosition.direction.getTurn()
    }

    // Down inclusive, up
    private fun Context.moveGuardX(x: Int) {
        if (guardPosition.x > x) {
            (guardPosition.x downTo x + 1).forEach {
                guardPosition.x -= 1
                visited.add(guardPosition)
            }
        } else {
            (guardPosition.x until x).forEach {
                guardPosition.x += 1
                visited.add(guardPosition)
            }
        }
    }

    private fun Context.moveGuardY(y: Int) {
        if (guardPosition.y > y) {
            (guardPosition.y downTo y + 1).forEach {
                guardPosition.y -= 1
                visited.add(guardPosition)
            }
        } else {
            (guardPosition.y until y).forEach {
                guardPosition.y += 1
                visited.add(guardPosition)
            }
        }
    }

    private fun Context.getBlockade(): Pos {
        return when (guardPosition.direction) {
            Direction.UP -> blockages.filter { it.y < guardPosition.y && it.x == guardPosition.x }.sortedBy { it.y }
                .last()

            Direction.RIGHT -> blockages.filter { it.x < guardPosition.x && it.y == guardPosition.y }.sortedBy { it.x }
                .last()

            Direction.DOWN -> blockages.filter { it.y > guardPosition.y && it.x == guardPosition.x }.sortedBy { it.y }
                .first()

            Direction.LEFT -> blockages.filter { it.x > guardPosition.x && it.y == guardPosition.y }.sortedBy { it.x }
                .first()
        }
    }

    override fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }
}