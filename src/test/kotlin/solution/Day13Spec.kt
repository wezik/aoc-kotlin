package solution

import app.wezik.aoc2024.solution.days.Day13Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day13Spec : FreeSpec({

    val solver = Day13Solver()

    "\"\" part 1" - {
        "example input" - {
            // given
            val input = listOf("")

            // expect
            solver.part1(input) shouldBe ""
        }
    }

    "\"\" part 2" - {
        "example input" - {
            // given
            val input = listOf("")

            // expect
            solver.part2(input) shouldBe ""
        }
    }

})
