package solution

import app.wezik.aoc2024.solution.days.Day03
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day03Spec : FreeSpec({
    val solver = Day03()

    "part 1 - mul parsing" {
        // given
        val input = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

        // expect
        solver.part1(input) shouldBe "161"
    }

    "part 2 - mul & do/don't parsing" - {

        "example input" {
            // given
            val input = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

            // expect
            solver.part2(input) shouldBe "48"
        }

        "do instructions persist between lines" {
            // given
            val input = listOf(
                "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undon't()?mul(8,5))",
                "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(5,5))",
            )

            // expect
            solver.part2(input) shouldBe "33"
        }

    }

})
