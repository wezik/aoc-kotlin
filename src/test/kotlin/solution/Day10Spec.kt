package solution

import app.wezik.aoc2024.solution.days.Day10
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Spec : FreeSpec({

    val solver = Day10()

    "part 1 - reachable tops" {
        // given
        val input = listOf(
            "89010123",
            "78121874",
            "87430965",
            "96549874",
            "45678903",
            "32019012",
            "01329801",
            "10456732",
        )

        // expect
        solver.part1(input) shouldBe "36"
    }

    "part 2 - available trails" {
        // given
        val input = listOf(
            "89010123",
            "78121874",
            "87430965",
            "96549874",
            "45678903",
            "32019012",
            "01329801",
            "10456732",
        )

        // expect
        solver.part2(input) shouldBe "81"
    }

})
