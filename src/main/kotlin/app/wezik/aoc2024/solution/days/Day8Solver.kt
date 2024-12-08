package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day8Solver : Solver {

    private data class Context(val antennas: List<Antenna>, val dimensions: Vector2D)
    private data class Antenna(val id: Char, val vector: Vector2D)
    private data class Vector2D(val x: Int, val y: Int) {
        override operator fun equals(other: Any?) = other is Vector2D && x == other.x && y == other.y
        operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
        operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    }

    private fun List<String>.parse(): Context {
        val dimensions = Vector2D(this.first().length, this.size)
        val antennas = this.withIndex().flatMap { row ->
            row.value.withIndex().map { col ->
                if (col.value == '.') return@map null
                Antenna(col.value, Vector2D(col.index, row.index))
            }.filterNotNull()
        }
        return Context(antennas, dimensions)
    }

    override fun part1(input: List<String>): String {
        val context = input.parse()
        return context.findAntiNodes().size.toString()
    }

    private fun Context.findAntiNodes(): Set<Vector2D> {
        return antennas.groupBy { it.id }.flatMap { (_, antennas) ->
            val vectors = antennas.map { it.vector }
            findAntiNodes(vectors, this.dimensions)
        }.toSet()
    }

    private fun findAntiNodes(vectors: List<Vector2D>, dimensions: Vector2D): Set<Vector2D> {
        val antiNodes = mutableSetOf<Vector2D>()
        for (primaryVector in vectors) {
            for (secondaryVector in vectors) {
                if (primaryVector == secondaryVector) continue
                val result = primaryVector + (primaryVector - secondaryVector)
                if (result.x < 0 || result.x > dimensions.x - 1 || result.y < 0 || result.y > dimensions.y - 1) continue
                antiNodes.add(result)
            }
        }
        return antiNodes
    }

    override fun part2(input: List<String>): String {
        return "0"
    }

}
