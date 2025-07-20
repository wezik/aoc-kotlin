package app.wezik.aoc.infrastructure.github

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.InputClient
import app.wezik.aoc.domain.Year
import app.wezik.aoc.infrastructure.streamToFile
import java.io.File
import java.net.URI

class GithubInputClient : InputClient {

    private val cachePath = "examples"
    private val exampleUrl = "https://raw.githubusercontent.com/wezik/aoc-kotlin/refs/heads/develop"

    override fun load(day: Day, year: Year): File {
        val file = cacheFile(day, year)
        return if (file.exists()) file else download(day, year)
    }

    private fun download(day: Day, year: Year): File {
        val file = cacheFile(day, year)
        url(day, year).streamToFile(file)
        return file
    }

    private fun url(day: Day, year: Year) = URI("$exampleUrl/${cacheFile(day, year)}").toURL()
    private fun cacheFile(day: Day, year: Year) = File("$cachePath/${year.value}/day${"%02d".format(day.value)}.txt")

}
