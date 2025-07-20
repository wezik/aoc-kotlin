package app.wezik.aoc.infrastructure.github

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.InputClient
import app.wezik.aoc.domain.StatusCode.*
import app.wezik.aoc.domain.Year
import app.wezik.aoc.infrastructure.streamToFile
import java.io.File
import java.net.URI

class GithubInputClient : InputClient {

    private val cachePath = "examples"
    private val exampleUrl = "https://raw.githubusercontent.com/wezik/aoc-kotlin/refs/heads/develop"

    override fun load(day: Day, year: Year): Result<File> {
        val file = cacheFile(day, year)
        if (file.exists()) {
            return Result.success(file)
        }
        val res = download(day, year)
        return res
    }

    private fun download(day: Day, year: Year): Result<File> {
        val file = cacheFile(day, year)
        val statusCode = url(day, year).streamToFile(file)
        return when (statusCode) {
            is OK -> Result.success(file)
            is NotFound -> Result.failure(Exception("remote file not found"))
            is InternalError -> Result.failure(Exception("internal error"))
            is RequestTimeout -> Result.failure(Exception("request timed out"))
            is UnknownStatus -> Result.failure(Exception("unknown status code $statusCode"))
        }
    }

    private fun url(day: Day, year: Year) = URI("$exampleUrl/${cacheFile(day, year)}").toURL()
    private fun cacheFile(day: Day, year: Year) = File("$cachePath/${year.value}/day${"%02d".format(day.value)}.txt")

}
