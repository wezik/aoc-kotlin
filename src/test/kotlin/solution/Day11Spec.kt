package solution

import app.wezik.aoc2024.solution.days.Day11
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day11Spec : FreeSpec({
    val solver = Day11()

    "part 1 - stones after 25 blinks" {
        // given
        val input = listOf(
            "125 17",
        )

        // expect
        solver.part1(input) shouldBe "55312"
    }

    "part 2 - stones after 75 blinks" {
        // given
        val input = listOf(
            "125 17",
        )

        // expect
        solver.part2(input) shouldBe "65601038650482"
    }

})
