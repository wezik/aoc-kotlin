package solution

import app.wezik.aoc2024.solution.days.Day1Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

class Day1Spec : FreeSpec({
    val solver = Day1Solver()

    "\"Distance\" part 1" - {

        "example input" {
            // given
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
            )

            // expect
            solver.part1(input) shouldBe "11"
        }

        "example input" {
            // given
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   5"
            )

            // expect
            solver.part1(input) shouldBe "13"
        }

    }

    "\"Similarity\" part 2" - {

        "example input" {
            // given
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
            )

            // expect
            solver.part2(input) shouldBe "31"
        }

        "big input timeout test".config(timeout = 500.milliseconds) {
            // given
            val input = mutableListOf("3   4", "4   3", "2   5", "1   3", "3   9", "3   3")
            (0..12).forEach { input.addAll(input) }

            // expect
            solver.part2(input) shouldBe "2080374784"
        }

    }
})
