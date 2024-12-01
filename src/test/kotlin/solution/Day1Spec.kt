package solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.Day1Solver

class Day1Spec : StringSpec({

    "empty run" {
        // Given
        val solver = Day1Solver()
        val input: List<String> = emptyList()

        // Expect
        solver.solve(input) shouldBe "0"
    }

    "day 1" {
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
        result shouldBe "11"
    }

    "day 1" {
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
        result shouldBe "13"
    }

})