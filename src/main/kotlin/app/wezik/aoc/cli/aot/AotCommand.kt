package app.wezik.aoc.cli.aot

import app.wezik.aoc.cli.dayOption
import app.wezik.aoc.cli.pathOption
import app.wezik.aoc.cli.yearOption
import app.wezik.aoc.domain.*
import app.wezik.aoc.infrastructure.AocFileDownloader
import app.wezik.aoc.infrastructure.AocFileLoader
import app.wezik.aoc.infrastructure.ReflectionSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main

// NOTE: test runner CLI entrypoint, it is separate to simplify the user experience and avoid option conflicts
fun main(args: Array<String>): Unit = AotCommand.main(args)

private object AotCommand : CliktCommand("aot") {
    // manual DI
    private val solutionRunner = SolutionRunner(
        solutionSelector = ReflectionSolutionSelector(),
        inputResolver = InputResolver(
            fileLoader = AocFileLoader(),
            fileDownloader = AocFileDownloader(),
            echo = { msg -> echo(msg) }
        ),
        echo = { msg -> echo(msg) }
    )

    // options
    private val day by dayOption()
    private val year by yearOption()
    private val path by pathOption()

    override fun run() {
        val result = path?.let { runCustomInput() } ?: runExampleInput()
    }

    private fun runCustomInput() : SolutionRunResult {
        val ctx = CustomContext(
            day = Day(day), 
            year = year?.let { Year(it) } ?: Year.recent(),
            path = path.toString()
        )

        return solutionRunner.run(ctx)
    }

    private fun runExampleInput() : SolutionRunResult {
        val ctx = ExampleContext(
            day = Day(day),
            year = year?.let { Year(it) } ?: Year.recent(),
        )

        return solutionRunner.run(ctx)
    }
}
