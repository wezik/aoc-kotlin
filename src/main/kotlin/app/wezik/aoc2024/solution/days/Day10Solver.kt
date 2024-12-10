package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day10Solver : Solver {

    private data class Vector2D(val x: Int, val y: Int)

    private data class Grid(val input: List<List<Int>>) {
        fun get(vector: Vector2D) = input.getOrNull(vector.y)?.getOrNull(vector.x)
    }

    private data class Context(val grid: Grid) {
        val scoreCache = mutableMapOf<Vector2D, Set<Vector2D>>()
    }

    private fun Context.toVectorList(): List<Vector2D> {
        return this.grid.input.withIndex().flatMap { (y, row) ->
            row.withIndex().map { (x, _) ->
                Vector2D(x, y)
            }
        }
    }

    private fun Context.getHikeScore(): Int {
        val starters = this.toVectorList().filter { this.grid.get(it) == 0 }
        var score = 0
        for (starter in starters) {
            score += this.findReachableEnds(starter).size
        }
        return score
    }

    private fun Context.findReachableEnds(vector: Vector2D): Set<Vector2D> {
        if (this.grid.get(vector) == 9) return this.scoreCache.getOrPut(vector) { setOf(vector) }
        val neighbors = this.pullValidNeighbors(vector)
        if (neighbors.isEmpty()) return this.scoreCache.getOrPut(vector) { setOf() }

        return this.scoreCache.getOrPut(vector) { neighbors.flatMap { this.findReachableEnds(it) }.toSet() }
    }

    private fun Context.pullValidNeighbors(vector: Vector2D): List<Vector2D> {
        val value = this.grid.get(vector)
        if (value == null) return emptyList()
        val east = Vector2D(vector.x + 1, vector.y)
        val west = Vector2D(vector.x - 1, vector.y)
        val north = Vector2D(vector.x, vector.y + 1)
        val south = Vector2D(vector.x, vector.y - 1)
        val neighbors = listOf(east, west, north, south)
        return neighbors.filter { neighbor ->
            val neighborValue = this.grid.get(neighbor)
            neighborValue != null && neighborValue == value + 1
        }
    }

    private fun List<String>.parse(): Context {
        return Context(Grid(this.map { it.map { it.digitToInt() } }))
    }

    override fun part1(input: List<String>): String {
        val context = input.parse()
        return context.getHikeScore().toString()
    }

    override fun part2(input: List<String>): String {
        return ""
    }
}
