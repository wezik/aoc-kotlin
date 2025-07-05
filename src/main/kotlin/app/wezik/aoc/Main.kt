package app.wezik.aoc
import java.io.File

// TODO: remove this once all solutions are moved to the new structure
interface Day {
	fun part1(input: File) = ""
	fun part2(input: File) = ""
}

fun main(args: Array<String>) {
    app.wezik.aoc.cli.start(args)
}
