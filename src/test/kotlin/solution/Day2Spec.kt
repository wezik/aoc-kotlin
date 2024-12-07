package solution

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day2Solver

class Day2Spec : FreeSpec({
    val solver = Day2Solver()

    "empty run" {
        // given
        val input: List<String> = emptyList()

        // when
        val solution = solver.solve(input)

        // then
        solution.part1.result shouldBe "0"
        solution.part2.result shouldBe "0"
    }

    "\"Safe reports\" part 1" - {

        "example input" {
            // given
            val input = listOf(
                "7 6 4 2 1", // safe
                "1 2 7 8 9", // unsafe
                "9 7 6 2 1", // unsafe
                "1 3 2 4 5", // unsafe
                "8 6 4 4 1", // unsafe
                "1 3 6 7 9"  // safe
            )

            // expect
            solver.part1(input) shouldBe "2"
        }

    }

    "\"Safe reports with toleration\" part 2" - {

        "example input" {
            // given
            val input = listOf(
                "7 6 4 2 1", // safe
                "1 2 7 8 9", // unsafe
                "9 7 6 2 1", // unsafe
                "1 3 2 4 5", // safe
                "8 6 4 4 1", // safe
                "1 3 6 7 9"  // safe
            )

            // expect
            solver.part2(input) shouldBe "4"
        }

        "handles unsafe on the beginning" {
            // given
            val input = listOf("1 3 2 4 5")

            // expect
            solver.part2(input) shouldBe "1"
        }

        "calculates difference properly" {
            // given
            val input = listOf("1 7 2 4 5")

            // expect
            solver.part2(input) shouldBe "1"
        }

        "calculates difference properly" {
            // given
            val input = listOf("1 2 7 8 9")

            // expect
            solver.part2(input) shouldBe "0"
        }

        "handles last entry direction change" {
            // given
            val input = listOf("1 2 4 6 3")

            // expect
            solver.part2(input) shouldBe "1"
        }

        "handles direction change early" {
            // given
            val input = listOf("1 2 1 4 5")

            // expect
            solver.part2(input) shouldBe "1"
        }

        "handles first entry being bad" {
            // given
            val input = listOf("6 1 3 4 5")

            // expect
            solver.part2(input) shouldBe "1"
        }

        data class BadActorTest(private val value: String, val index: Int) : WithDataTestName {
            val input = listOf(value)
            override fun dataTestName() = "for bad actor on $index index"
        }

        "rising bad actors" - {
            // given
            withData(
                BadActorTest("8 5 6 8 9", 0),
                BadActorTest("5 8 6 8 9", 1),
                BadActorTest("5 6 4 7 9", 2),
            ) { value ->
                // expect
                solver.part2(value.input) shouldBe "1"
            }
        }

        "lowering bad actors" - {
            // given
            withData(
                BadActorTest("2 5 4 3 1", 0),
                BadActorTest("4 6 2 1 0", 1),
                BadActorTest("4 3 5 2 1", 2),
            ) { value ->
                // expect
                solver.part2(value.input) shouldBe "1"
            }
        }

    }

})
