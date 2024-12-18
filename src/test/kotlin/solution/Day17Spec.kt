package solution

import app.wezik.aoc2024.solution.days.Day17
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day17Spec : FreeSpec({
    val solver = Day17()

    "part 1 - chronospatial computer" {
        // given
        val input = listOf(
            "Register A: 729",
            "Register B: 0",
            "Register C: 0",
            "",
            "Program: 0,1,5,4,3,0"
        )

        // expect
        solver.part1(input) shouldBe "4,6,3,5,6,3,5,2,1,0"
    }

    "part 2 - reconstruct input for register A" {
        // given
        val input = listOf(
            "Register A: 2024",
            "Register B: 0",
            "Register C: 0",
            "",
            "Program: 0,3,5,4,3,0"
        )

        // expect
        solver.part2(input) shouldBe "117440"
    }

})
