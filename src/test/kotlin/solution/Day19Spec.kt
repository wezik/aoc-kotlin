package solution

import app.wezik.aoc2024.solution.days.Day19Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day19Spec : FreeSpec({

    val solver = Day19Solver()

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
