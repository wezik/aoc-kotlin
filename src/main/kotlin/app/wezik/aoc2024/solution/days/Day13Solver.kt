package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day13Solver : Solver {

    private operator fun Vector.plus(other: Vector): Vector = Vector(x + other.x, y + other.y)
    private data class Vector(val x: Long, val y: Long)
    private data class State(val destination: Vector, val buttons: List<Pair<Vector, Int>>)

    private fun List<String>.parse(fixConversion: Boolean): List<State> {
        val buttonQueue = mutableListOf<Vector>()
        val states = mutableListOf<State>()

        forEach { line ->
            when (line.firstOrNull()) {
                'B' -> buttonQueue.add(
                    line.split(',').let { (xString, yString) ->
                        Vector(xString.split('+')[1].toLong(), yString.split('+')[1].toLong())
                    }
                )

                'P' -> {
                    val offset = if (fixConversion) Vector(10_000_000_000_000L, 10_000_000_000_000L) else Vector(0L, 0L)
                    val vector = line.split(',').let { (xString, yString) ->
                        Vector(xString.split('=')[1].toLong(), yString.split('=')[1].toLong()) + offset
                    }
                    states.add(State(vector, listOf(buttonQueue.removeFirst() to 3, buttonQueue.removeFirst() to 1)))
                }
            }
        }
        return states
    }

    private fun List<String>.findCheapestPaths(fixConversion: Boolean = false) =
        this.parse(fixConversion).map { it.findCheapestPath(it.destination, it.buttons) }.filterNotNull().sum()

    private fun State.findCheapestPath(destination: Vector, buttons: List<Pair<Vector, Int>>): Long? {
        val vecA = buttons[0].first
        val vecB = buttons[1].first

        val countA = (destination.x * vecB.y - destination.y * vecB.x).toDouble() / (vecA.x * vecB.y - vecA.y * vecB.x)
        val countB = (destination.x - vecA.x * countA).toDouble() / (vecB.x)

        if (countA % 1 != 0.0 || countB % 1 != 0.0) return null
        return countA.toLong() * 3 + countB.toLong()
    }

    override fun part1(input: List<String>) = input.findCheapestPaths().toString()
    override fun part2(input: List<String>) = input.findCheapestPaths(fixConversion = true).toString()

}
