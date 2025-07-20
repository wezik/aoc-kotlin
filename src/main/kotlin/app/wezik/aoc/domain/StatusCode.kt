package app.wezik.aoc.domain

sealed class StatusCode(open val value: Int) {
    override fun toString() = value.toString()

    companion object {
        fun from(value: Int): StatusCode = when (value) {
            200 -> OK
            404 -> NotFound
            408 -> RequestTimeout
            500 -> InternalError
            else -> UnknownStatus(value)
        }
    }

    object OK : StatusCode(200)
    object NotFound : StatusCode(404)
    object RequestTimeout : StatusCode(408)
    object InternalError : StatusCode(500)

    data class UnknownStatus(override val value: Int) : StatusCode(value)
}

