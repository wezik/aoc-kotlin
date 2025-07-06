package app.wezik.aoc.infrastructure
import app.wezik.aoc.domain.FileDownloader
import java.io.File
import java.net.HttpURLConnection
import java.net.URI

class AocFileDownloader : FileDownloader {

    private companion object {
        const val EXAMPLE_SOURCE_FORMAT = "https://raw.githubusercontent.com/wezik/aoc-kotlin/refs/heads/develop/inputs/%s/example/Day%02d.txt"
        const val ADVENT_SOURCE_FORMAT = "https://adventofcode.com/%s/day/%d/input"

        // NOTE: these entries should be synchronized with the ones in [AocFileLoader], it probably should be remade a bit
        const val CACHE_ADVENT_SOURCE_FORMAT = "inputs/%s/Day%02d.txt"
        const val CACHE_EXAMPLE_SOURCE_FORMAT = "inputs/%s/example/Day%02d.txt"
    }

    override fun downloadInput(day: Int, year: Int, sessionCookie: String): File? {
        val file = File(CACHE_ADVENT_SOURCE_FORMAT.format(year, day))

        val result = runCatching { download(ADVENT_SOURCE_FORMAT.format(year, day), file, sessionCookie) }
        if (result.isFailure) {
            return null // handle errors silently
        }

        return file
    }

    override fun downloadExample(day: Int, year: Int): File? {
        val file = File(CACHE_EXAMPLE_SOURCE_FORMAT.format(year, day))

        val result = runCatching { download(EXAMPLE_SOURCE_FORMAT.format(year, day), file) }
        if (result.isFailure) {
            return null // handle errors silently
        }

        return file
    }

    private fun download(url: String, output: File, sessionCookie: String? = null) {
        val url = URI(url).toURL()
        var connection: HttpURLConnection? = null

        try {
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000

                if (sessionCookie != null) {
                    setRequestProperty("Cookie", "session=$sessionCookie")
                }
            }

            output.parentFile?.mkdirs() // ensure parent directories exist

            connection.inputStream.use { input ->
                output.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            // rethrow the exception
            throw RuntimeException("Failed to download file from $url", e)
        } finally {
            connection?.disconnect()
        }
    }
}
