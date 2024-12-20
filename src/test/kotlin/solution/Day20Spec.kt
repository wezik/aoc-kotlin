package solution

import app.wezik.aoc2024.solution.days.Day20
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day20Spec : FreeSpec({
    val solver = Day20()

    "part 1 - race condition single picosecond cheat" {
        // given
        val input = listOf(
            "###############",
            "#...#...#.....#",
            "#.#.#.#.#.###.#",
            "#S#...#.#.#...#",
            "#######.#.#.###",
            "#######.#.#...#",
            "#######.#.###.#",
            "###..E#...#...#",
            "###.#######.###",
            "#...###...#...#",
            "#.#####.#.###.#",
            "#.#...#.#.#...#",
            "#.#.#.#.#.#.###",
            "#...#...#...###",
            "###############",
        )

        // expect
        solver.part1(input, 2) shouldBe "44"
    }

    "part 2 - race condition cheats up to 20 picoseconds" {
        // given
        val input = listOf(
            "###############",
            "#...#...#.....#",
            "#.#.#.#.#.###.#",
            "#S#...#.#.#...#",
            "#######.#.#.###",
            "#######.#.#...#",
            "#######.#.###.#",
            "###..E#...#...#",
            "###.#######.###",
            "#...###...#...#",
            "#.#####.#.###.#",
            "#.#...#.#.#...#",
            "#.#.#.#.#.#.###",
            "#...#...#...###",
            "###############",
        )

        // expect
        solver.part2(input, 70) shouldBe "41"
    }

})
