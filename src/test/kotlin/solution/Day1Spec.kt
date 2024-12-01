package solution

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.Day1Solver
import kotlin.time.Duration.Companion.milliseconds

class Day1Spec : DescribeSpec({
    describe("day 1") {

        it("empty run") {
            // Given
            val solver = Day1Solver()
            val input: List<String> = emptyList()

            // Expect
            solver.solve(input).first shouldBe "0"
            solver.solve(input).second shouldBe "0"
        }

        it("distance") {
            // Given
            val solver = Day1Solver()
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
            )

            // When
            val result = solver.solve(input)

            // Then
            result.first shouldBe "11"
        }

        it("distance alt") {
            // Given
            val solver = Day1Solver()
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   5"
            )

            // When
            val result = solver.solve(input)

            // Then
            result.first shouldBe "13"
        }

        it("similarity") {
            // Given
            val solver = Day1Solver()
            val input = listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second shouldBe "31"
        }

        it("similarity big input").config(timeout = 500.milliseconds) {
            // Given
            val solver = Day1Solver()
            val input = mutableListOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
            )
            (0..12).forEach {
                input.addAll(input)
            }

            // When
            val result = solver.solve(input)

            // Then
            result.second shouldBe "2080374784"

        }

    }
})