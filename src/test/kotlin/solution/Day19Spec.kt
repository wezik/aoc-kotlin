package solution

import app.wezik.aoc2024.solution.days.Day19
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day19Spec : FreeSpec({
    val solver = Day19()

    "part 1 - check possible linen designs" {
        // given
        val input = listOf(
            "r, wr, b, g, bwu, rb, gb, br",
            "\n",
            "brwrr",
            "bggr",
            "gbbr",
            "rrbgbr",
            "ubwu",
            "bwurrg",
            "brgr",
            "bbrgwb",
        )

        // expect
        solver.part1(input) shouldBe "6"
    }

    "part 2 - count possible linen designs" {
        // given
        val input = listOf(
            "r, wr, b, g, bwu, rb, gb, br",
            "\n",
            "brwrr",
            "bggr",
            "gbbr",
            "rrbgbr",
            "ubwu",
            "bwurrg",
            "brgr",
            "bbrgwb",
        )

        // expect
        solver.part2(input) shouldBe "16"
    }

})
