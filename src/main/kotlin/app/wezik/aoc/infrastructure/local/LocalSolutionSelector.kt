package app.wezik.aoc.infrastructure.local

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionSelector
import app.wezik.aoc.domain.Year

class LocalSolutionSelector : SolutionSelector {

    // selects the solution for the given day and year with reflection, avoiding errors and mapping them to nulls
    override fun select(day: Day, year: Year): Solution? {
        val paddedDay = "%02d".format(day.value)
        val className = "app.wezik.aoc.solutions.y${year.value}.Day$paddedDay"

        return runCatching {
            val kInstance = Class.forName(className).kotlin.objectInstance
            return when (kInstance) {
                is Solution ->  kInstance
                else -> null
            }
        }.getOrNull()
    }
}
