package solution

import app.wezik.aoc2024.solution.days.Day21
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day21Spec : FreeSpec({
    val solver = Day21()

    "part 1 - Keypad conundrum" {
        // given
        val input = listOf(
            "029A",
            "980A",
            "179A",
            "456A",
            "379A",
        )

        // expect
        solver.part1(input) shouldBe "126384"
    }

    "part 2 - Keypad conundrum with depth of 25" {
        // given
        val input = listOf(
            "029A",
            "980A",
            "179A",
            "456A",
            "379A",
        )

        // expect
        solver.part2(input) shouldBe "154115708116294"
    }

})
