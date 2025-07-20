package app.wezik.aoc.infrastructure

import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

fun URL.streamToFile(file: File, block: (HttpURLConnection) -> Unit = {}) {
    try {
        openConnection().useAsHttp { connection -> 
            // set request properties
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 15000
                readTimeout = 15000
            }

            // allows for custom request properties
            block(connection)

            // ensure parent directories exist
            file.parentFile?.mkdirs() 

            // stream input to output
            connection.inputStream.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    } catch (e: Exception) {
        // wrap the exception
        throw RuntimeException("Failed to download file from $this", e)
    }
}

// util function to simplify the code
private fun <T> URLConnection.useAsHttp(block: (HttpURLConnection) -> T): T {
    this as HttpURLConnection
    try {
        return block(this)
    } finally {
        disconnect()
    }
}

