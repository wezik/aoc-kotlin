package solution

import app.wezik.aoc2024.solution.days.Day9Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day9Spec : FreeSpec({

    val solver = Day9Solver()

    "\"Check sum of moved file parts\" part 1" - {
        "example input" {
            // given
            val input = listOf("2333133121414131402")

            // expect
            solver.part1(input) shouldBe "1928"
        }
    }

    "\"Check sum of moved file blocks\" part 2" - {
        "example input" {
            // given
            val input = listOf("2333133121414131402")

            // expect
            solver.part2(input) shouldBe "2858"
        }
    }

})
