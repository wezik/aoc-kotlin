package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.days.Day6Solver

class Day6Spec : FreeSpec({

    val solver = Day6Solver()

    "\"Calculate guards path\" part 1" - {
        "example input" {
            // given
            val input = listOf(
                "....#.....",
                ".........#",
                "..........",
                "..#.......",
                ".......#..",
                "..........",
                ".#..^.....",
                "........#.",
                "#.........",
                "......#...",
            )

            // expect
            solver.part1(input) shouldBe 41
        }
    }

})
