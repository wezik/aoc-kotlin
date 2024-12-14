package solution

import app.wezik.aoc2024.solution.days.Day09
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day09Spec : FreeSpec({
    val solver = Day09()

    "part 1 - fragmenting files" {
        // given
        val input = listOf("2333133121414131402")

        // expect
        solver.part1(input) shouldBe "1928"
    }

    "part 2 - moving whole files" {
        // given
        val input = listOf("2333133121414131402")

        // expect
        solver.part2(input) shouldBe "2858"
    }

})
