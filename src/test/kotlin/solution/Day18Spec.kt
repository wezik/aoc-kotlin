package solution

import app.wezik.aoc2024.solution.days.Day18
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day18Spec : FreeSpec({
    val solver = Day18()

    "part 1 - ram run" {
        // given
        val input = listOf(
            "5,4",
            "4,2",
            "4,5",
            "3,0",
            "2,1",
            "6,3",
            "2,4",
            "1,5",
            "0,6",
            "3,3",
            "2,6",
            "5,1",
            "1,2",
            "5,5",
            "2,5",
            "6,5",
            "1,4",
            "0,4",
            "6,4",
            "1,1",
            "6,1",
            "1,0",
            "0,5",
            "1,6",
            "2,0",
        )

        // expect
        solver.part1(input, 12) shouldBe "22"
    }

    "part 2 - which byte will block the exit" {
        // given
        val input = listOf(
            "5,4",
            "4,2",
            "4,5",
            "3,0",
            "2,1",
            "6,3",
            "2,4",
            "1,5",
            "0,6",
            "3,3",
            "2,6",
            "5,1",
            "1,2",
            "5,5",
            "2,5",
            "6,5",
            "1,4",
            "0,4",
            "6,4",
            "1,1",
            "6,1",
            "1,0",
            "0,5",
            "1,6",
            "2,0",
        )

        // expect
        solver.part2(input) shouldBe "6,1"
    }

})
