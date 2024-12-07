package solution

import app.wezik.aoc2024.solution.days.Day7Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day7Spec : FreeSpec({

    val solver = Day7Solver()

    "\"Find and sum equations that can be made true\" part 1" - {
        "example input" {
            // given
            val input = listOf(
                "190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20",
            )

            // expect
            solver.part1(input) shouldBe "3749"
        }
        "all + operators" {
            // given
            val input = listOf("7: 4 2 1")

            // expect
            solver.part1(input) shouldBe "7"
        }

        "all * operators" {
            // given
            val input = listOf("16: 4 2 2")

            // expect
            solver.part1(input) shouldBe "16"
        }
        "single * operators" {
            // given
            val input = listOf(
                "18: 8 2 2",
                "20: 1 9 2",
            )
            // expect
            solver.part1(input) shouldBe "38"
        }
    }

    "\"Find and sum equations that can be made true with joined (||) operators\" part 2" - {
        "example input" {
            // given
            val input = listOf(
                "190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20",
            )

            // expect
            solver.part2(input) shouldBe "11387"
        }
        "|| operator works" {
            // given
            val input = listOf("1090: 10 90")

            // expect
            solver.part2(input) shouldBe "1090"
        }
    }

})
