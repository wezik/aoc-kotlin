package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day5Solver

class Day5Spec : FreeSpec({

    val solver = Day5Solver()

    "\"Detect updates with correct ordering\" part 1" - {
        "small input" {
            // given
            val input = listOf(
                "47|53",
                "",
                "47,51,53",
                "53,47,51",
            )

            // expect
            solver.part1(input) shouldBe "51"
        }
        "example input" {
            // given
            val input = listOf(
                "47|53",
                "97|13",
                "97|61",
                "97|47",
                "75|29",
                "61|13",
                "75|53",
                "29|13",
                "97|29",
                "53|29",
                "61|53",
                "97|53",
                "61|29",
                "47|13",
                "75|47",
                "97|75",
                "47|61",
                "75|61",
                "47|29",
                "75|13",
                "53|13",
                "",
                "75,47,61,53,29",
                "97,61,53,29,13",
                "75,29,13",
                "75,97,47,61,53",
                "61,13,29",
                "97,13,75,29,47"
            )

            // expect
            solver.part1(input) shouldBe "143"
        }
    }
    "\"Detect updates with incorrect ordering and fix them\" part 2" - {
        "small input" {
            // given
            val input = listOf(
                "47|53",
                "53|51",
                "",
                "47,53,51",
                "53,47,51",
            )

            // expect
            solver.part2(input) shouldBe "53"
        }
        "example input" {
            // given
            val input = listOf(
                "47|53",
                "97|13",
                "97|61",
                "97|47",
                "75|29",
                "61|13",
                "75|53",
                "29|13",
                "97|29",
                "53|29",
                "61|53",
                "97|53",
                "61|29",
                "47|13",
                "75|47",
                "97|75",
                "47|61",
                "75|61",
                "47|29",
                "75|13",
                "53|13",
                "",
                "75,47,61,53,29",
                "97,61,53,29,13",
                "75,29,13",
                "75,97,47,61,53",
                "61,13,29",
                "97,13,75,29,47"
            )

            // expect
            solver.part2(input) shouldBe "123"
        }
    }

})
