package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day4Solver

class Day4Spec : FreeSpec({
    val solver = Day4Solver()

    "empty run" {
        // and given
        val input: List<String> = emptyList()

        // when
        val solution = solver.solve(input)

        // then
        solution.part1.result shouldBe "0"
        solution.part2.result shouldBe "0"
    }

    "\"'XMAS' finder\" part 1" - {
        "example input" {
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

            // expect
            solver.part1(input) shouldBe 18
        }

        "detects directions" - {
            "left" {
                // given
                val input = listOf("MMSAMX")

                // expect
                solver.part1(input) shouldBe 1
            }
            "right" {
                // given
                val input = listOf("MXMASX")

                // expect
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
                solver.part1(input) shouldBe 1
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
            solver.part1(input) shouldBe 4
        }
    }

    "\"X shaped 'MAS'\" part 2" - {
        "single MAS search" {
            //given
            val input = listOf(
                "M.S",
                ".A.",
                "M.S",
            )

            // expect
            solver.part2(input) shouldBe 1
        }
        "example input" {
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

            // expect
            solver.part2(input) shouldBe 9
        }
        "correctly handles repeating characters in a single cross" {
            // given
            val input = listOf(
                "M.S",
                ".A.",
                "S.M",
            )

            // expect
            solver.part2(input) shouldBe 0
        }
    }

})
