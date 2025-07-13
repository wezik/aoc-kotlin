package app.wezik.aoc.domain

// TODO: usually booleans are code smeels but since I am passing flags here, I am not yet sure, think on it
sealed interface Context {
    val day: Day
    val year: Year 
    val part1: Boolean
    val part2: Boolean
}

data class DefaultContext(
    override val day: Day,
    override val year: Year,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
    val sessionCookie: String?,
) : Context

data class ExampleContext(
    override val day: Day,
    override val year: Year,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
) : Context

data class CustomContext(
    override val day: Day,
    override val year: Year,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
    val path: String,
) : Context
