package solution

import app.wezik.aoc2024.solution.days.Day8Solver
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day8Spec : FreeSpec({

    val solver = Day8Solver()

    "\"Unique frequencies that contain an anti-node\" part 1" - {
        "example input" {
            // given
            val input = listOf(
                "............",
                "........0...",
                ".....0......",
                ".......0....",
                "....0.......",
                "......A.....",
                "............",
                "............",
                "........A...",
                ".........A..",
                "............",
                "............",
            )

            // expect
            solver.part1(input) shouldBe "14"
        }
    }

})
