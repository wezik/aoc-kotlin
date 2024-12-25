package app.wezik.aoc.y2015

import app.wezik.aoc.Day
import java.io.File

class Day01 : Day {
    override fun part1(input: File): String {
        val str = input.readText()
        val result = str.count { it == '(' } - str.count { it == ')' }
        return "$result"
    }

    override fun part2(input: File): String {
        val str = input.readText()
        
        var floor = 0
        for (i in 0 until str.length) {
            if (str[i] == '(') floor++
            else if (str[i] == ')') floor--
            if (floor < 0) return "${i + 1}"
        }

        return "None"
    }
}
