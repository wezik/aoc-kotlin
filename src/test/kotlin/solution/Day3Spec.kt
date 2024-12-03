package solution

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.example.solution.solver.days.Day3Solver

class Day3Spec : DescribeSpec({
    describe("day 3") {

        val solver = Day3Solver()

        it("empty run") {
            // Given
            val input: List<String> = emptyList()

            // When
            val solution = solver.solve(input)
            // Expect
            solution.first.value shouldBe "0"
            solution.second.value shouldBe "0"
        }

        it("example mul calculation") {
            // Given
            val input: List<String> = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

            // Expect
            solver.solve(input).first.value shouldBe "161"
        }

        it("example mul calculation with do instructions") {
            // Given
            val input: List<String> =
                listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

            // Expect
            solver.solve(input).second.value.shouldBe("48")
        }

        it("do instructions persisting between lines") {
            // Given
            val input: List<String> =
                listOf(
                    "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undon't()?mul(8,5))",
                    "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(5,5))",
                    )

            // Expect
            solver.solve(input).second.value.shouldBe("33")
        }
    }
})
