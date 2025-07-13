package app.wezik.aoc.domain

interface SolutionSelector {
    fun select(day: Day, year: Year): Solution?
}
