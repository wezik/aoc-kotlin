package app.wezik.aoc.infrastructure

import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

fun URL.streamToFile(file: File, block: PropertyBuilder.() -> Unit = {}) {
    try {
        openConnection().useAsHttp { connection -> 
            // set request properties
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 15000
                readTimeout = 15000
            }

            // register custom request properties
            val propBuilder = PropertyBuilder()
            block(propBuilder)
            propBuilder.build().forEach { (k, v) ->
                connection.setRequestProperty(k, v)
            }

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

// utilized to build custom request properties in nice way
class PropertyBuilder {
    private val properties = mutableMapOf<String, String>()
    fun build() = properties
    infix fun String.to(value: String) {
        properties[this] = value
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

