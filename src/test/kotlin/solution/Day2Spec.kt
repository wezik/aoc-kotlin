package solution

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day1Solver
import org.example.solution.solver.days.Day2Solver
import kotlin.time.Duration.Companion.milliseconds

class Day2Spec : DescribeSpec({
    describe("day 2") {

        val solver = Day2Solver()

        it("empty run") {
            // Given
            val input: List<String> = emptyList()

            // Expect
            solver.solve(input).first.value shouldBe "0"
            solver.solve(input).second.value shouldBe "0"
        }

        it("safe reports") {
            // Given
            val input = listOf(
                "7 6 4 2 1", // safe
                "1 2 7 8 9", // unsafe
                "9 7 6 2 1", // unsafe
                "1 3 2 4 5", // unsafe
                "8 6 4 4 1", // unsafe
                "1 3 6 7 9"  // safe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.first.value shouldBe "2"
        }

        it("safe reports with toleration") {
            // Given
            val input = listOf(
                "7 6 4 2 1", // safe
                "1 2 7 8 9", // unsafe
                "9 7 6 2 1", // unsafe
                "1 3 2 4 5", // safe
                "8 6 4 4 1", // safe
                "1 3 6 7 9"  // safe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "4"
        }

        it("2nd level unsafe toleration") {
            // Given
            val input = listOf(
                "1 3 2 4 5", // safe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "1"
        }

        it("big step on tolerated input") {
            // Given
            val input = listOf(
                "1 7 2 4 5", // safe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "1"
        }

        it("big step on tolerated input doesn't stay") {
            // Given
            val input = listOf(
                "1 2 7 8 9", // unsafe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "0"
        }

        it("last level change direction tolerated") {
            // Given
            val input = listOf(
                "1 2 4 6 3", // unsafe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "1"
        }

        it("3rd level change direction tolerated") {
            // Given
            val input = listOf(
                "1 2 1 4 5", // unsafe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "1"
        }

        it("1st level bad one tolerated") {
            // Given
            val input = listOf(
                "6 1 3 4 5", // unsafe
            )

            // When
            val result = solver.solve(input)

            // Then
            result.second.value shouldBe "1"
        }

        it("rising bad actor on A") {
            // Given
            val input = listOf("8 5 6 8 9") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }

        it("rising bad actor on B") {
            // Given
            val input = listOf("5 8 6 8 9") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }

        it("rising bad actor on C") {
            // Given
            val input = listOf("5 6 4 7 9") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }

        it("lowering bad actor on A") {
            // Given
            val input = listOf("2 5 4 3 1") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }
        it("lowering bad actor on B") {
            // Given
            val input = listOf("4 6 2 1 0") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }
        it("lowering bad actor on C") {
            // Given
            val input = listOf("4 3 5 2 1") // safe

            // Expect
            solver.solve(input).second.value shouldBe "1"
        }
    }
})
