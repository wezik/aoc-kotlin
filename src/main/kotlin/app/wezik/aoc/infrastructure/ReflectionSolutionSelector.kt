package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionPart
import app.wezik.aoc.domain.SolutionSelector
import java.io.File

class ReflectionSolutionSelector : SolutionSelector {
    // TODO: below should be put in place of the current temporary solution
    // private val classNameFormat = "app.wezik.aoc.domain.solutions.y%s.Day%02dKt"
    
    private val classNameFormat = "app.wezik.aoc.y%s.Day%02dKt"

    // selects the solution for the given day and year with reflection, avoiding errors and mapping them to nulls
    override fun select(day: Int, year: Int): Solution? {
        val className = classNameFormat.format(year, day)

        val fetch = runCatching {
            val clazz = Class.forName(className)
            val part1 = clazz.resolvePart("part1")
            val part2 = clazz.resolvePart("part2")
            part1 to part2
        }

        val (part1, part2) = fetch.getOrNull() ?: return null

        return Solution(day, year, part1, part2)
    }

    private fun Class<*>.resolvePart(part: String): SolutionPart? {
        val method = runCatching {
            val method = this.getMethod(part, File::class.java)
            return { input -> method.invoke(null, input) as String }
        }

        return method.getOrNull()
    }
}
