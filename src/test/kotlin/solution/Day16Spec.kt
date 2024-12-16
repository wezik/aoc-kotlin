package solution

import app.wezik.aoc2024.solution.days.Day16
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day16Spec : FreeSpec({
    val solver = Day16()

    "part 1 - " {
        // given
        val input = listOf(
            "#################",
            "#...#...#...#..E#",
            "#.#.#.#.#.#.#.#.#",
            "#.#.#.#...#...#.#",
            "#.#.#.#.###.#.#.#",
            "#...#.#.#.....#.#",
            "#.#.#.#.#.#####.#",
            "#.#...#.#.#.....#",
            "#.#.#####.#.###.#",
            "#.#.#.......#...#",
            "#.#.###.#####.###",
            "#.#.#...#.....#.#",
            "#.#.#.#####.###.#",
            "#.#.#.........#.#",
            "#.#.#.#########.#",
            "#S#.............#",
            "#################",
        )

        // expect
        solver.part1(input) shouldBe "11048"
    }

    "part 2 - " {
        // given
        val input = listOf("")

        // expect
        solver.part2(input) shouldBe ""
    }

})
