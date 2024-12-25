package app.wezik.aoc

import kotlin.reflect.full.createInstance
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import java.io.File

interface Day {
    fun part1(input: File) = ""
    fun part2(input: File) = ""
}

fun main(args: Array<String>) {
    val config = Config(args)
    val day = loadDay(config.year, config.day)

    val startP1 = System.nanoTime()
    val answerP1 = day.part1(config.input)
    val durationP1 = (System.nanoTime() - startP1).toDuration(DurationUnit.NANOSECONDS).toString(DurationUnit.MILLISECONDS, 3)
    println("Part 1 ($durationP1): $answerP1")

    val startP2 = System.nanoTime()
    val answerP2 = day.part2(config.input)
    val durationP2 = (System.nanoTime() - startP2).toDuration(DurationUnit.NANOSECONDS).toString(DurationUnit.MILLISECONDS, 3)
    println("Part 2 ($durationP2): $answerP2")
}

// dynamically loads the day through reflection
private fun loadDay(year: Int, day: Int): Day {
    println("Loading day $day $year")
    val packageName = "app.wezik.aoc.y$year"
    val className = "Day${day.toString().padStart(2, '0')}"
    val fullName = "$packageName.$className"
    return try {
        val inst = Class.forName(fullName).getDeclaredConstructor().newInstance()
        if (inst is Day) inst else error("$fullName does not implement Day interface")
    } catch (e: ClassNotFoundException) {
        error("$fullName does not exist")
    }
}
