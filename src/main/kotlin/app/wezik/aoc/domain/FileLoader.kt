package app.wezik.aoc.domain

import java.io.File

// NOTE: this part of code uses java.io.File but it doesn't seem like a worth effort to wrap it into some domain abstraction
interface FileLoader {
    fun loadInput(day: Int, year: Int): File?
    fun loadExample(day: Int, year: Int): File?
    fun loadCustom(input: String): File?
}
