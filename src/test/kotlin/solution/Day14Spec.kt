package solution

import app.wezik.aoc2024.solution.days.Day14
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day14Spec : FreeSpec({
    val solver = Day14()

    "part 1 - safety factor after 100 generations" {
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
    "part 2 - lowest safety factor second (christmas tree)" {
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
        solver.part2Custom(input, 11, 7) shouldBe "5"
    }

})
