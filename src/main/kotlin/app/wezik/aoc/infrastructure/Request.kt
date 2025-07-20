package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.StatusCode
import java.io.File
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLConnection

fun URL.streamToFile(file: File, block: PropertyBuilder.() -> Unit = {}): StatusCode {
    var statusCode: StatusCode = StatusCode.UnknownStatus(-1)
    try {
        openConnection().useAsHttp { connection -> 
            // set request properties
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
            }

            // register custom request properties
            val propBuilder = PropertyBuilder()
            block(propBuilder)
            propBuilder.build().forEach { (k, v) ->
                connection.setRequestProperty(k, v)
            }

            // capture status code
            statusCode = StatusCode.from(connection.responseCode)

            if (statusCode is StatusCode.OK) {
                // ensure parent directories exist
                file.parentFile?.mkdirs() 

                // stream input to output
                connection.inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    } catch (e: SocketTimeoutException) {
        statusCode = StatusCode.RequestTimeout
    } catch (e: Exception) {
        // handle errors silently, client should operate on status codes
        statusCode = StatusCode.InternalError
    }
    return statusCode
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

