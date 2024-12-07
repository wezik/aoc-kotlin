package solution

import app.wezik.aoc2024.solution.days.Day6Solverv2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day6Spec : FreeSpec({

    val solver = Day6Solverv2()

    "\"Calculate guards path\" part 1" - {
        "example input" {
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
    }

    "\"Get the number of obstacles that you could put to loop the guard\" part 2" - {
        "example input" {
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
    }

})
