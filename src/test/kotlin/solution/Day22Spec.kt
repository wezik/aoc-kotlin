package solution

import app.wezik.aoc2024.solution.days.Day22
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day22Spec : FreeSpec({
    val solver = Day22()

    "part 1 - Monkey market pseudo-random price formula" {
        // given
        val input = listOf(
            "1",
            "10",
            "100",
            "2024",
        )

        // expect
        solver.part1(input) shouldBe "37327623"
    }

    "part 2 - Monkey market pseudo-random price prediction" {
        // given
        val input = listOf(
            "1",
            "2",
            "3",
            "2024",
        )

        // expect
        solver.part2(input) shouldBe "23"
    }

})
