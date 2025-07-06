package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.FileLoader
import java.io.File
import java.net.URI
import java.net.HttpURLConnection

// TODO: this implementation is temporary and should be replaced with a proper solution soon
class AocFileLoader : FileLoader {
    private companion object {
        // NOTE: these entries should be synchronized with the ones in [AocFileDownloader], it probably should be remade a bit
        const val CACHE_ADVENT_SOURCE_FORMAT = "inputs/%s/Day%02d.txt"
        const val CACHE_EXAMPLE_SOURCE_FORMAT = "inputs/%s/example/Day%02d.txt"
    }

    override fun loadInput(day: Int, year: Int): File? {
        val cachedFile = File(CACHE_ADVENT_SOURCE_FORMAT.format(year, day))
        return if (cachedFile.exists()) cachedFile else null
    }

    override fun loadExample(day: Int, year: Int): File? {
        val cachedFile = File(CACHE_EXAMPLE_SOURCE_FORMAT.format(year, day))
        return if (cachedFile.exists()) cachedFile else null
    }

    override fun loadCustom(input: String) = File(input)
}
