package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day5Solver

class Day5Spec : FreeSpec({

    "day 5" - {

        val solver = Day5Solver()

        fun foo(valIs: String, shouldBe: Int) {
            println("IS: $valIs SHOULD BE: $shouldBe")
        }

        "part 1" - {
            "foo" {
                // given
                val input = listOf(
                    "47|53",
                    "",
                    "47,51,53",
                    "53,47,51",
                )
                foo(solver.solve(input).first.value, 51)
                // expect
                solver.solve(input).first.value shouldBe "51"
            }
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
            foo(solver.solve(input).first.value, 143)
            // expect
            solver.solve(input).first.value shouldBe "143"
        }
        "part 2" - {
            "foo" {
                // given
                val input = listOf(
                    "47|53",
                    "",
                    "47,51,53",
                    "53,47,51",
                )
                foo(solver.solve(input).second.value, 53)
                // expect
                solver.solve(input).second.value shouldBe "53"
            }
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
            foo(solver.solve(input).second.value, 123)
            // expect
            solver.solve(input).second.value shouldBe "123"
        }
    }
})
