package app.wezik.aoc2024.utils

import java.io.File

fun readFrom(path: String): List<String> = File(path).readLines()
