package app.wezik.aoc.domain

sealed interface RunContext {
    val day: Int
    val year: Int
    val part1: Boolean
    val part2: Boolean
}

data class DefaultContext(
    override val day: Int,
    override val year: Int,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
    val sessionCookie: String?,
) : RunContext

data class ExampleContext(
    override val day: Int,
    override val year: Int,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
) : RunContext

data class CustomContext(
    override val day: Int,
    override val year: Int,
    override val part1: Boolean = true,
    override val part2: Boolean = true,
    val path: String,
) : RunContext
