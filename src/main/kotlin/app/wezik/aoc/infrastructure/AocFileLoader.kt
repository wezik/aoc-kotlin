package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.FileLoader
import java.io.File

// TODO: this implementation is temporary and should be replaced with a proper solution soon
class AocFileLoader : FileLoader {
    private object Sources {
        const val EXAMPLE_SOURCE_FORMAT = "https://github.com/wezik/aoc-kotlin/blob/develop/inputs/%s/example/Day%02d.txt"
        const val AOC_SOURCE_FORMAT = "https://adventofcode.com/%s/day/%d/input"
        const val CACHE_SOURCE_FORMAT = "inputs/%s/Day%02d.txt"
        const val CACHE_EXAMPLE_SOURCE_FORMAT = "inputs/%s/example/Day%02d.txt"
    }

    override fun load(day: Int, year: Int) =
        File(Sources.CACHE_EXAMPLE_SOURCE_FORMAT.format(year, day))

    override fun loadExample(day: Int, year: Int) = 
        File(Sources.CACHE_EXAMPLE_SOURCE_FORMAT.format(year, day))

    override fun loadCustom(day: Int, year: Int, input: String) =
        File(Sources.CACHE_EXAMPLE_SOURCE_FORMAT.format(year, day))
}
