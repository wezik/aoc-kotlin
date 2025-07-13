package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionSelector
import app.wezik.aoc.domain.Year

class ReflectionSolutionSelector : SolutionSelector {
    private val packageNameFormat = "app.wezik.aoc.solutions.y%s"

    // selects the solution for the given day and year with reflection, avoiding errors and mapping them to nulls
    override fun select(day: Day, year: Year): Solution? {
        val paddedDay = "%02d".format(day.value)
        val className = "app.wezik.aoc.solutions.y${year.value}.Day$paddedDay"

        return try {
            val clazz = Class.forName(className)
            val kClass = clazz.kotlin
            if (kClass.objectInstance is Solution) {
                kClass.objectInstance as Solution
            } else {
                null
            }
        } catch (e: ClassNotFoundException) {
            null // No such class for the given day/year
        } catch (e: Throwable) {
            null // Fail safe
        }
    }
}
