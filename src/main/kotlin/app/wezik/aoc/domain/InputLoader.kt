package app.wezik.aoc.domain

import java.io.File

interface InputLoader {
    fun load(day: Int, year: Int): File?
    fun loadExample(day: Int, year: Int): File?
    fun loadCustom(day: Int, year: Int, input: String): File?
}
