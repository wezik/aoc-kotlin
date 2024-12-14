package solution

import app.wezik.aoc2024.solution.days.Day12
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day12Spec : FreeSpec({
    val solver = Day12()

    "part 1 - fence price" - {
        "small example" {
            // given
            val input = listOf(
                "AAAA",
                "BBCD",
                "BBCC",
                "EEEC",
            )

            // expect
            solver.part1(input) shouldBe "140"
        }
        "contained example" {
            // given
            val input = listOf(
                "OOOOO",
                "OXOXO",
                "OOOOO",
                "OXOXO",
                "OOOOO",
            )

            // expect
            solver.part1(input) shouldBe "772"
        }

        "large example" {
            // given
            val input = listOf(
                "RRRRIICCFF",
                "RRRRIICCCF",
                "VVRRRCCFFF",
                "VVRCCCJFFF",
                "VVVVCJJCFE",
                "VVIVCCJJEE",
                "VVIIICJJEE",
                "MIIIIIJJEE",
                "MIIISIJEEE",
                "MMMISSJEEE",
            )

            // expect
            solver.part1(input) shouldBe "1930"
        }
    }

    "part 2 - discounted fence price" - {
        "small example" {
            // given
            val input = listOf(
                "AAAA",
                "BBCD",
                "BBCC",
                "EEEC",
            )

            // expect
            solver.part2(input) shouldBe "80"
        }
        "medium example" {
            // given
            val input = listOf(
                "EEEEE",
                "EXXXX",
                "EEEEE",
                "EXXXX",
                "EEEEE",
            )

            // expect
            solver.part2(input) shouldBe "236"
        }
        "large example" {
            // given
            val input = listOf(
                "RRRRIICCFF",
                "RRRRIICCCF",
                "VVRRRCCFFF",
                "VVRCCCJFFF",
                "VVVVCJJCFE",
                "VVIVCCJJEE",
                "VVIIICJJEE",
                "MIIIIIJJEE",
                "MIIISIJEEE",
                "MMMISSJEEE",
            )

            // expect
            solver.part2(input) shouldBe "1206"
        }
        "contained edge case" {
            // given
            val input = listOf(
                "OOOOO",
                "OAOAO",
                "OAAAO",
                "OOOOO",
            )

            // expect
            solver.part2(input) shouldBe "220"
        }
    }

})
