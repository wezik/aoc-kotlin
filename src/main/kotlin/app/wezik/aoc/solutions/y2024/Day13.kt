package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.solutions.y2024.Day13.plus

object Day13 : Solution(
    day = 13,
    year = 2024,
) {

    private operator fun Vector.plus(other: Vector): Vector = Vector(x + other.x, y + other.y)
    private data class Vector(val x: Long, val y: Long)
    private data class Machine(val prize: Vector, val buttons: Pair<Vector, Vector>)

    private fun List<String>.parse(part2: Boolean): List<Machine> {
        val machines = mutableListOf<Machine>()
        val iterator = iterator()
        while (iterator.hasNext()) {
            var line = iterator.next()
            if (line.isBlank()) continue
            val buttonA = line.split(',').let { Vector(it[0].split('+')[1].toLong(), it[1].split('+')[1].toLong()) }
            line = iterator.next()
            val buttonB = line.split(',').let { Vector(it[0].split('+')[1].toLong(), it[1].split('+')[1].toLong()) }
            line = iterator.next()
            var prize = line.split(',').let { Vector(it[0].split('=')[1].toLong(), it[1].split('=')[1].toLong()) }
            if (part2) prize += Vector(10_000_000_000_000, 10_000_000_000_000)
            machines.add(Machine(prize, buttonA to buttonB))
        }
        return machines
    }

    private fun List<String>.findSolution(part2: Boolean = false) = parse(part2).map { it.findLowestPrice() }.sum()

    private fun Machine.findLowestPrice(): Long {
        val (vecA, vecB) = buttons

        val denominator = vecA.x * vecB.y - vecA.y * vecB.x
        val countA = (prize.x * vecB.y - prize.y * vecB.x).toDouble() / denominator
        val countB = (prize.x - vecA.x * countA).toDouble() / vecB.x

        if (countA % 1 != 0.0 || countB % 1 != 0.0) return 0L
        return countA.toLong() * 3 + countB.toLong()
    }

    override fun part1(input: SolutionInput) = input.file.readLines().findSolution().toString()
    override fun part2(input: SolutionInput) = input.file.readLines().findSolution(part2 = true).toString()

}
