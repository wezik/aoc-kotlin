package solution

import app.wezik.aoc2024.solution.days.Day10Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Spec : FreeSpec({

    val solver = Day10Solver()

    "\"Sum of reachable ends score\" part 1" - {
        "example input" {
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
    }

    "\"Sum of trailhead scores\" part 2" - {
        "example input" {
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
    }

})
