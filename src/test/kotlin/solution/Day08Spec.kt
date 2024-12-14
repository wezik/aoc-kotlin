package solution

import app.wezik.aoc2024.solution.days.Day08
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day08Spec : FreeSpec({
    val solver = Day08()

    "part 1 - anti nodes" {
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

    "part 2 - anti nodes with resonance" - {
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
            solver.part2(input) shouldBe "34"
        }

        "2 points create a \"resonance\" line" {
            // given
            val input = listOf(
                "............",
                "............",
                "............",
                "............",
                "...A........",
                "............",
                ".A..........",
            )

            // expect
            solver.part2(input) shouldBe "4"
        }
        "4 points of 2 frequencies create a crossing \"resonance\" line" {
            // given
            val input = listOf(
                "............",
                "............",
                "............",
                "............",
                "...A........",
                "....B.......",
                ".A...B......",
            )

            // expect
            solver.part2(input) shouldBe "9" // 9 since one is a duplicate
        }
    }

})
