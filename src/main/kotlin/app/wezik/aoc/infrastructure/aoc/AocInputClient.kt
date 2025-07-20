package app.wezik.aoc.infrastructure.aoc

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.InputClient
import app.wezik.aoc.domain.StatusCode.*
import app.wezik.aoc.domain.Year
import app.wezik.aoc.infrastructure.streamToFile
import java.io.File
import java.net.URI

class AocInputClient(private val sessionCookie: String?) : InputClient {

    private val adventUrl = "https://adventofcode.com"
    private val cachePath = ".cache/inputs"

    override fun load(day: Day, year: Year): Result<File> {
        val file = cacheFile(day, year)
        if (file.exists()) {
            return Result.success(file)
        }
        val res = download(day, year)
        return res
    }

    private fun download(day: Day, year: Year): Result<File> {
        if (sessionCookie == null) return Result.failure(Exception("session cookie is required to download input"))
        val file = cacheFile(day, year)
        val statusCode = url(day, year).streamToFile(file) { "Cookie" to "session=$sessionCookie" }
        return when (statusCode) {
            is OK -> Result.success(file)
            is NotFound -> Result.failure(Exception("remote file not found"))
            is InternalError -> Result.failure(Exception("internal error - could be outdated/incorrect session cookie"))
            is RequestTimeout -> Result.failure(Exception("request timed out"))
            is UnknownStatus -> Result.failure(Exception("unknown status code $statusCode"))
        }
    }

    private fun url(day: Day, year: Year) = URI("$adventUrl/${year.value}/day/${day.value}/input").toURL()
    private fun cacheFile(day: Day, year: Year) = File("$cachePath/${year.value}/day${day.value}.txt")

}
