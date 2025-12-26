package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import java.util.*
import kotlin.math.sqrt

object Day08 : Solution() {

    data class Vec3(val x: Int, val y: Int, val z: Int)
    fun Vec3.distanceTo(other: Vec3): Float {
        val dx = (x - other.x).toFloat()
        val dy = (y - other.y).toFloat()
        val dz = (z - other.z).toFloat()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val positions = input.lines.map { it.split(",").map { it.toInt() } }.map { (x, y, z) -> Vec3(x, y, z) }
        val connectionCount = if (input.isTestRun) 10 else 1000

        val lookupIndices = LinkedList<Pair<Int, Int>>()

        for (i in 0 until positions.size) {
            for (j in i + 1 until positions.size) {
                lookupIndices += i to j
            }
        }

        lookupIndices.sortBy { (a, b) -> positions[a].distanceTo(positions[b]) }

        // Pair of connections made and actual connections
        val circuits = mutableListOf<MutableList<Pair<Int, Int>>>()

        outer@ for (i in 0..<connectionCount) {
            val (a, b) = lookupIndices.removeFirst()

            val validCircuits = circuits.filter { connections ->
                connections.any { (ca, cb) -> ca == a || cb == a || ca == b || cb == b }
            }

            when (validCircuits.size) {
                0 -> circuits.add(mutableListOf(a to b))
                1 -> validCircuits.first().add(a to b)
                else -> {
                    // reduce to single connection as they connect to each other from now on
                    val connections = validCircuits.first()
                    for (connection in validCircuits.drop(1)) connections.addAll(connection)
                    connections.add(a to b)
                    circuits.removeAll { connections ->
                        connections.any { (ca, cb) -> ca == a || cb == a || ca == b || cb == b }
                    }
                    circuits.add(connections)
                }
            }
        }

        val counts = circuits
            .map { it.flatMap { (a, b) -> setOf(a, b) }.toSet().size }
            .toSet()

        val (a, b, c) = counts.sortedByDescending { it }.take(3)
        return SolutionResult.Success(a * b * c)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val positions = input.lines.map { it.split(",").map { it.toInt() } }.map { (x, y, z) -> Vec3(x, y, z) }
        val lookupIndices = LinkedList<Pair<Int, Int>>()

        for (i in 0 until positions.size) {
            for (j in i + 1 until positions.size) {
                lookupIndices += i to j
            }
        }

        lookupIndices.sortBy { (a, b) -> positions[a].distanceTo(positions[b]) }

        // Pair of connections made and actual connections
        val circuits = mutableListOf<MutableList<Pair<Int, Int>>>()
        var lastUsedCoordinates = lookupIndices.first()

        val unusedIndices = positions.indices.toMutableSet()
        outer@ while (unusedIndices.isNotEmpty()) {
            val (a, b) = lookupIndices.removeFirst()
            lastUsedCoordinates = a to b
            unusedIndices.remove(a)
            unusedIndices.remove(b)

            val validCircuits = circuits.filter { connections ->
                connections.any { (ca, cb) -> ca == a || cb == a || ca == b || cb == b }
            }

            when (validCircuits.size) {
                0 -> circuits.add(mutableListOf(a to b))
                1 -> validCircuits.first().add(a to b)
                else -> {
                    // reduce to single connection as they connect to each other from now on
                    val connections = validCircuits.first()
                    for (connection in validCircuits.drop(1)) connections.addAll(connection)
                    connections.add(a to b)
                    circuits.removeAll { connections ->
                        connections.any { (ca, cb) -> ca == a || cb == a || ca == b || cb == b }
                    }
                    circuits.add(connections)
                }
            }
        }

        val (a, b) = lastUsedCoordinates.let { (aIndex, bIndex) -> positions[aIndex] to positions[bIndex] }

        return SolutionResult.Success(a.x * b.x)
    }
}
