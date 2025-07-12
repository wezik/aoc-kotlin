package app.wezik.aoc.domain

sealed interface RunContext {
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
) : RunContext

data class ExampleContext(
    override val day: Day,
    override val year: Year,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
) : RunContext

data class CustomContext(
    override val day: Day,
    override val year: Year,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
    val path: String,
) : RunContext
