package solution

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.Day1Solver

class Day1Spec : StringSpec({

    "day 1" {
        // Given
        val solver = Day1Solver()

        // When
        val result = solver.solve("Hello World")

        // Then
        result shouldBe "Hello World"
    }

})