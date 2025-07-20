package app.wezik.aoc.infrastructure.local

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.InputClient
import app.wezik.aoc.domain.Year
import java.io.File
import java.nio.file.Path

class LocalInputClient(private val path: Path): InputClient {

    override fun load(day: Day, year: Year): Result<File> {
        val file = File(path.toString())
        // it should be checked by validation in CLI layer but it's here just in case
        if (!file.exists()) return Result.failure(Exception("file does not exist"))
        return Result.success(file)
    }

}
