package solution

import app.wezik.aoc2024.solution.days.Day11Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day11Spec : FreeSpec({

    val solver = Day11Solver()

    "\"Amount of stones after 25 blinks\" part 1" - {
        "example input" {
            // given
            val input = listOf(
                "125 17",
            )

            // expect
            solver.part1(input) shouldBe "55312"
        }
    }

})
