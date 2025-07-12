package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day14 : Solution() {

    // constants mentioned in the puzzle description
    private const val HEIGHT = 103
    private const val WIDTH = 101

    override fun part1(input: SolutionInput) = part1Custom(input.lines, WIDTH, HEIGHT)
    fun part1Custom(input: List<String>, width: Int, height: Int): SolutionResult {
        val robots = input.map {
            val numbers = Regex("""-?\d+""").findAll(it).map { it.value.toInt() }.toList()
            Pair(numbers[0], numbers[1]) to Pair(numbers[2], numbers[3])
        }

        val result = mutableListOf<Pair<Int, Int>>()

        // position = px, py, velocity = vx, vy, result = rx, ry
        for ((position, velocity) in robots) {
            val (px, py) = position
            val (vx, vy) = velocity
            var (rx, ry) = (px + vx * 100) % width to (py + vy * 100) % height
            if (rx < 0) rx = width + rx
            if (ry < 0) ry = height + ry
            result.add(rx to ry)
        }

        val quadScore = IntArray(4)

        // hm = horizontal median, vm = vertical median
        val vm = (height - 1).toDouble() / 2
        val hm = (width - 1).toDouble() / 2

        for ((x, y) in result) {
            if (x.toDouble() == hm || y.toDouble() == vm) continue
            when {
                x < hm && y < vm -> quadScore[0]++
                x > hm && y < vm -> quadScore[1]++
                x < hm && y > vm -> quadScore[2]++
                x > hm && y > vm -> quadScore[3]++
            }
        }

        var score = 1
        quadScore.forEach { score *= it }
        return Success(score.toString())
    }

    override fun part2(input: SolutionInput) = part2Custom(input.lines, WIDTH, HEIGHT)
    fun part2Custom(input: List<String>, width: Int, height: Int): SolutionResult {
        val robots = input.map {
            val numbers = Regex("""-?\d+""").findAll(it).map { it.value.toInt() }.toList()
            Pair(numbers[0], numbers[1]) to Pair(numbers[2], numbers[3])
        }

        var minSafety = Int.MAX_VALUE
        var bestIteration = 0

        for (second in (1..width * height)) {
            val result = mutableListOf<Pair<Int, Int>>()

            // position = px, py, velocity = vx, vy, result = rx, ry
            for ((position, velocity) in robots) {
                val (px, py) = position
                val (vx, vy) = velocity
                var (rx, ry) = (px + vx * second) % width to (py + vy * second) % height
                if (rx < 0) rx = width + rx
                if (ry < 0) ry = height + ry
                result.add(rx to ry)
            }

            val quadScore = IntArray(4)

            // hm = horizontal median, vm = vertical median
            val vm = (height - 1).toDouble() / 2
            val hm = (width - 1).toDouble() / 2

            for ((x, y) in result) {
                if (x.toDouble() == hm || y.toDouble() == vm) continue
                when {
                    x < hm && y < vm -> quadScore[0]++
                    x > hm && y < vm -> quadScore[1]++
                    x < hm && y > vm -> quadScore[2]++
                    x > hm && y > vm -> quadScore[3]++
                }
            }

            // sf = safety factor
            var sf = 1
            quadScore.forEach { sf *= it }

            if (sf < minSafety) {
                minSafety = sf
                bestIteration = second
            }
        }
        return Success(bestIteration.toString())
    }

}
