package solution

import app.wezik.aoc2024.solution.days.Day06
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Spec : FreeSpec({
    val solver = Day06()

    "part 1 - guards path" {
        // given
        val input = listOf(
            "....#.....",
            ".........#",
            "..........",
            "..#.......",
            ".......#..",
            "..........",
            ".#..^.....",
            "........#.",
            "#.........",
            "......#...",
        )

        // expect
        solver.part1(input) shouldBe "41"
    }

    "part 2 - obstacles to loop the guard" {
        // given
        val input = listOf(
            "....#.....",
            ".........#",
            "..........",
            "..#.......",
            ".......#..",
            "..........",
            ".#..^.....",
            "........#.",
            "#.........",
            "......#...",
        )

        // expect
        solver.part2(input) shouldBe "6"
    }

})
