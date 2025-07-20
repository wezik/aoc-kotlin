package app.wezik.aoc.domain

import java.io.File

interface InputClient {
    fun load(day: Day, year: Year): File
}
