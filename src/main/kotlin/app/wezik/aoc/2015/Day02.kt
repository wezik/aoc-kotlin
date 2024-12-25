package app.wezik.aoc.y2015

import app.wezik.aoc.Day
import java.io.File

class Day02 : Day {
    override fun part1(input: File): String {
        var total = 0
        for (line in input.readLines()) {
            val (l, w, h) = line.trim().split('x').map { it.toInt() }
            total += (2 * l * w) + (2 * w * h) + (2 * h * l) + minOf(l * w, w * h, h * l)
        }
        return "$total"
    }

    override fun part2(input: File): String {
        var total = 0
        for (line in input.readLines()) {
            val (l, w, h) = line.trim().split('x').map { it.toInt() }
            total += l * w * h + listOf(l, w, h).sorted().let { (a, b) -> 2 * a + 2 * b }
        }
        return "$total"
    }
}
