package app.wezik.aoc.domain
import java.io.File

// NOTE: this part of code uses java.io.File but it doesn't seem like a worth effort to wrap it into some domain abstraction
interface FileDownloader {
    fun downloadInput(day: Int, year: Int, sessionCookie: String): File?
    fun downloadExample(day: Int, year: Int): File?
}
