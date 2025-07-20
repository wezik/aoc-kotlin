package app.wezik.aoc.infrastructure.aoc

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.InputClient
import app.wezik.aoc.domain.Year
import app.wezik.aoc.infrastructure.streamToFile
import com.github.ajalt.clikt.core.UsageError
import java.io.File
import java.net.URI

class AocInputClient(private val sessionCookie: String?) : InputClient {

    private val adventUrl = "https://adventofcode.com"
    private val cachePath = ".cache/inputs"

    override fun load(day: Day, year: Year): File {
        val file = cacheFile(day, year)
        return if (file.exists()) file else download(day, year)
    }

    private fun download(day: Day, year: Year): File {
        if (sessionCookie == null) throw UsageError("session cookie is required to download input")
        val file = cacheFile(day, year)
        url(day, year).streamToFile(file) { "Cookie" to "session=$sessionCookie" }
        return file
    }

    private fun url(day: Day, year: Year) = URI("$adventUrl/${year.value}/day/${day.value}/input").toURL()
    private fun cacheFile(day: Day, year: Year) = File("$cachePath/${year.value}/day${day.value}.txt")

}
