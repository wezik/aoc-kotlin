package app.wezik.aoc.domain

import java.io.File

// NOTE: this uses java.io.File but it doesn't seem like a worth effort to wrap it into some domain abstraction
interface FileLoader {
    fun load(day: Int, year: Int): File?
    fun loadExample(day: Int, year: Int): File?
    fun loadCustom(day: Int, year: Int, input: String): File
}
