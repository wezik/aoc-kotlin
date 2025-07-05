package app.wezik.aoc.domain

interface SolutionSelector {
    fun select(day: Int, year: Int): Solution?
}
