package app.wezik.aoc.domain

import com.github.ajalt.clikt.core.UsageError

class InputResolver(
    private val fileLoader: FileLoader,
    private val fileDownloader: FileDownloader,
    private val echo: (Any?) -> Unit,
) {
    fun fetchAdventInput(day: Int, year: Int, sessionCookie: String?): SolutionInput {
        val cache = fileLoader.loadInput(day, year)
        if (cache != null) return SolutionInput(file = cache)

        if (sessionCookie == null) throw UsageError("session cookie is required if not running against example or custom input")

        echo("Downloading input for day $day of $year")
        val input = fileDownloader.downloadInput(day, year, sessionCookie) ?: throw UsageError("failed to download input")
        return SolutionInput(file = input)
    }

    fun fetchExampleInput(day: Int, year: Int): SolutionInput {
        val cache = fileLoader.loadExample(day, year)
        if (cache != null) return SolutionInput(file = cache)

        echo("Downloading example input for day $day of $year")
        val input = fileDownloader.downloadExample(day, year) ?: throw UsageError("failed to download example")
        return SolutionInput(file = input)
    }

    fun fetchCustomInput(day: Int, year: Int, path: String): SolutionInput {
        val cache = fileLoader.loadCustom(path)
        if (cache != null) return SolutionInput(file = cache)
        throw UsageError("failed to load custom input")
    }
}
