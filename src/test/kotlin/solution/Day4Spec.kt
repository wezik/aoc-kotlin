package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day4Solver

class Day4Spec : FreeSpec({

    "day 4" - {
        // Given
        val solver = Day4Solver()

        "empty run" {
            // and given
            val input: List<String> = emptyList()

            // when
            val solution = solver.solve(input)

            // then
            solution.first.value shouldBe "0"
            solution.second.value shouldBe "0"
        }

        "part 1" - {
            "small xmas search" {
                // given
                val input = listOf(
                    "MMMSXXMASM",
                    "MSAMXMSMSA",
                    "AMXSXMAAMM",
                    "MSAMASMSMX",
                    "XMASAMXAMM",
                    "XXAMMXXAMA",
                    "SMSMSASXSS",
                    "SAXAMASAAA",
                    "MAMMMXMMMM",
                    "MXMXAXMASX",
                )

                // when
                val solution = solver.solve(input)

                // then
                solution.first.value shouldBe "18"
            }

            "detects directions" - {
                "left" {
                    // given
                    val input = listOf("MMSAMX")
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "right" {
                    // given
                    val input = listOf("MXMASX")
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "up" {
                    // given
                    val input = listOf(
                        "MMMSM",
                        "MMMAM",
                        "MMMMM",
                        "MMMXM",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "down" {
                    // given
                    val input = listOf(
                        "MMXAM",
                        "MMMAM",
                        "MMAMM",
                        "MMSXM",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "left-up" {
                    // given
                    val input = listOf(
                        "MSSAM",
                        "MMAMM",
                        "MMMMX",
                        "MMMMX",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "right-up" {
                    // given
                    val input = listOf(
                        "MMASM",
                        "MMAMM",
                        "MMMMX",
                        "XMMMX",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "left-down" {
                    // given
                    val input = listOf(
                        "MSMMX",
                        "MMMMM",
                        "MMAMX",
                        "MSMMX",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
                "right-down" {
                    // given
                    val input = listOf(
                        "MXAMX",
                        "MMMMM",
                        "MMAAX",
                        "MMMMS",
                    )
                    // expect
                    solver.solve(input).first.value shouldBe "1"
                }
            }
            "detects multiples" {
                // given
                val input = listOf(
                    "MXMASM",
                    "MMMMAM",
                    "MAMMMM",
                    "MSAMXM",
                )
                // expect
                solver.solve(input).first.value shouldBe "4"
            }
        }


    }
})
