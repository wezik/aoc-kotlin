package solution

import app.wezik.aoc2024.solution.days.Day01
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

class Day01Spec : FreeSpec({
    val solver = Day01()

    "part 1 - distance" {
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

    "part 2 - similarity" {
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

})
