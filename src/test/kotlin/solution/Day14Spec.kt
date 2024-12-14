package solution

import app.wezik.aoc2024.solution.days.Day14Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day14Spec : FreeSpec({

    val solver = Day14Solver()

    "\"Safety factor of robots grid after 100 generations\" part 1" - {
        "example input" {
            // given
            val input = listOf(
                "=0,4 v=3,-3",
                "=6,3 v=-1,-3",
                "=10,3 v=-1,2",
                "=2,0 v=2,-1",
                "=0,0 v=1,3",
                "=3,0 v=-2,-2",
                "=7,6 v=-1,-3",
                "=3,0 v=-1,-2",
                "=9,3 v=2,3",
                "=7,3 v=-1,2",
                "=2,4 v=2,-3",
                "=9,5 v=-3,-3",
            )

            // expect
            solver.part1Custom(input, 11, 7) shouldBe "12"
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
