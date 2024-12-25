package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import java.io.File

class Day08 : Day {

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

    override fun part1(input: File): String {
        val context = input.readLines().parse()
        return context.findAntiNodes().size.toString()
    }

    override fun part2(input: File): String {
        val context = input.readLines().parse()
        return context.findAntiNodes(part2 = true).size.toString()
    }

    private fun Context.findAntiNodes(part2: Boolean = false): Set<Vector2D> {
        return antennas.groupBy { it.id }.flatMap { (_, antennas) ->
            val vectors = antennas.map { it.vector }
            findAntiNodes(vectors, this.dimensions, part2)
        }.toSet()
    }

    private fun findAntiNodes(vectors: List<Vector2D>, dimensions: Vector2D, part2: Boolean = false): Set<Vector2D> {
        val antiNodes = mutableSetOf<Vector2D>()
        for (primaryVector in vectors) {
            for (secondaryVector in vectors) {
                if (primaryVector == secondaryVector) continue
                val result = primaryVector + (primaryVector - secondaryVector)
                if (result.isOutOfBounds(dimensions)) continue
                antiNodes.add(result)
                if (part2) {
                    antiNodes.add(primaryVector)
                    antiNodes.add(secondaryVector)
                    var nextResult = result + (primaryVector - secondaryVector)
                    while (!nextResult.isOutOfBounds(dimensions)) {
                        antiNodes.add(nextResult)
                        nextResult = nextResult + (primaryVector - secondaryVector)
                    }
                }
            }
        }
        return antiNodes
    }

    private fun Vector2D.isOutOfBounds(dimensions: Vector2D): Boolean {
        return x < 0 || x > dimensions.x - 1 || y < 0 || y > dimensions.y - 1
    }

}
