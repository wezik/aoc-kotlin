package app.wezik.aoc.cli.aoc

import app.wezik.aoc.cli.dayOption
import app.wezik.aoc.cli.sessionCookieOption
import app.wezik.aoc.cli.yearOption
import app.wezik.aoc.domain.SolutionRunContext
import app.wezik.aoc.domain.SolutionRunner
import app.wezik.aoc.infrastructure.aoc.AocInputClient
import app.wezik.aoc.infrastructure.local.LocalSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main

// CLI entrypoint
fun main(args: Array<String>): Unit = AocCommand.main(args)

private object AocCommand : CliktCommand("aoc") {

    // options
    private val day by dayOption()
    private val year by yearOption()
    private val sessionCookie by sessionCookieOption()

    override fun run() {
        val result = solutionRunner().run(SolutionRunContext(
            day = day,
            year = year,
        ))
    }

    // manual DI
    private fun solutionRunner() = SolutionRunner(
        solutionSelector = LocalSolutionSelector(),
        inputClient = AocInputClient(resolveSessionCookie()),
        echo = { msg -> echo(msg) } // forwards access to echo to domain layer
    )

    private fun resolveSessionCookie() = sessionCookie ?: System.getenv("ADVENT_COOKIE")
}
