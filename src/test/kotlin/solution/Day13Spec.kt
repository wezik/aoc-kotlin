package solution

import app.wezik.aoc2024.solution.days.Day13
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day13Spec : FreeSpec({
    val solver = Day13()

    "part 1 - tickets to get prize" {
        // given
        val input = listOf(
            "Button A: X+94, Y+34",
            "Button B: X+22, Y+67",
            "Prize: X=8400, Y=5400",
            "",
            "Button A: X+26, Y+66",
            "Button B: X+67, Y+21",
            "Prize: X=12748, Y=12176",
            "",
            "Button A: X+17, Y+86",
            "Button B: X+84, Y+37",
            "Prize: X=7870, Y=6450",
            "",
            "Button A: X+69, Y+23",
            "Button B: X+27, Y+71",
            "Prize: X=18641, Y=10279",
        )

        // expect
        solver.part1(input) shouldBe "480"
    }

    "part 2 - tickets to get prize bumped by (10^12)" {
        // given
        val input = listOf(
            "Button A: X+94, Y+34",
            "Button B: X+22, Y+67",
            "Prize: X=8400, Y=5400",
            "",
            "Button A: X+26, Y+66",
            "Button B: X+67, Y+21",
            "Prize: X=12748, Y=12176",
            "",
            "Button A: X+17, Y+86",
            "Button B: X+84, Y+37",
            "Prize: X=7870, Y=6450",
            "",
            "Button A: X+69, Y+23",
            "Button B: X+27, Y+71",
            "Prize: X=18641, Y=10279",
        )

        // expect
        solver.part2(input) shouldBe "875318608908"
    }

})
