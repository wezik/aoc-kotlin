package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import java.util.PriorityQueue

class Day13Solver : Solver {

    private data class Vector(val x: Int, val y: Int)

    private operator fun Vector.plus(other: Vector): Vector = Vector(x + other.x, y + other.y)

    private data class State(val destination: Vector, val buttons: List<Pair<Vector, Int>>)

    private fun List<String>.parse(): List<State> {
        val buttonQueue = mutableListOf<Vector>()
        val states = mutableListOf<State>()
        this.forEach {
            when (it.firstOrNull()) {
                null -> return@forEach
                'B' -> {
                    val (xString, yString) = it.split(',')
                    val (_, x) = xString.split('+')
                    val (_, y) = yString.split('+')
                    buttonQueue.add(Vector(x.toInt(), y.toInt()))
                }

                'P' -> {
                    val (xString, yString) = it.split(',')
                    val (_, x) = xString.split('=')
                    val (_, y) = yString.split('=')
                    val destination = Vector(x.toInt(), y.toInt())
                    states.add(
                        State(destination, listOf(buttonQueue.removeFirst() to 3, buttonQueue.removeFirst() to 1))
                    )
                }
            }
        }
        return states
    }

    private fun List<String>.findCheapestPaths() =
        this.parse().map { it.findCheapestPath(it.destination, it.buttons) }.filterNotNull().sum()

    private data class Path(var cost: Int = 0, var position: Vector = Vector(0, 0))

    private fun State.findCheapestPath(destination: Vector, buttons: List<Pair<Vector, Int>>): Int? {
        val pq = PriorityQueue<Path>(compareBy { it.cost })
        val visited = mutableSetOf<Vector>()

        // Bootstrap
        pq.add(Path())
        while (pq.isNotEmpty()) {
            val current = pq.poll()

            // Reached the destination, return the cost
            if (current.position == destination) return current.cost

            // Mark as visited
            if (visited.contains(current.position)) continue
            visited.add(current.position)

            // Explore all moves
            for ((move, cost) in buttons) {
                val newPosition = current.position + move
                val newCost = current.cost + cost

                if (!visited.contains(newPosition) && newCost <= 400) {
                    pq.add(Path(newCost, newPosition))
                }
            }
        }
        // Queue is empty, no path found
        return null
    }

    override fun part1(input: List<String>) = input.findCheapestPaths().toString()
    override fun part2(input: List<String>) = ""

}
