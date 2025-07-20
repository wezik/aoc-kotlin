package app.wezik.aoc.cli.aot

import app.wezik.aoc.cli.dayOption
import app.wezik.aoc.cli.pathOption
import app.wezik.aoc.cli.yearOption
import app.wezik.aoc.domain.SolutionRunContext
import app.wezik.aoc.domain.SolutionRunner
import app.wezik.aoc.infrastructure.github.GithubInputClient
import app.wezik.aoc.infrastructure.local.LocalInputClient
import app.wezik.aoc.infrastructure.local.LocalSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main

// test runner CLI entrypoint, it is separate to simplify the user experience and avoid convoluted option constraints
fun main(args: Array<String>): Unit = AotCommand.main(args)

private object AotCommand : CliktCommand("aot") {

    // options
    private val day by dayOption()
    private val year by yearOption()
    private val path by pathOption()

    override fun run() {
        val result = solutionRunner().run(SolutionRunContext(
            day = day,
            year = year,
        ))
    }

    // manual DI
    private fun solutionRunner() = SolutionRunner(
        solutionSelector = LocalSolutionSelector(),
        inputClient = path?.let { LocalInputClient(it) } ?: GithubInputClient(),
        echo = { msg -> echo(msg) } // forwards access to echo to domain layer
    )
}
