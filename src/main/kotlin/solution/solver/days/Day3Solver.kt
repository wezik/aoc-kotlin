package org.example.solution.solver.days

import org.example.solution.solver.Result
import org.example.solution.solver.Solver
import org.example.solution.time
import org.example.solution.toNanoDuration
import kotlin.text.isDigit

class Day3Solver : Solver {

    data class Slider(val value: String) {

        private var index = 0

        fun next(): Char? {
            if (index >= value.length) return null
            return value[index++]
        }

        fun previous() {
            index--
        }

        fun jumpTo(char: Char): Char? {
            var c = next()
            while (c != null && c != char) {
                c = next()
            }
            return c
        }
    }

    override fun solve(input: List<String>): Pair<Result, Result> {
        var part1Sum = 0
        val part1Time = time {
            val sliders = input.map { it -> Slider(it.lowercase()) }
            part1Sum = sliders.map { collectAndSumMuls(it) }.sum()
        }
        val part1Result = Result(part1Sum.toString(), part1Time.toNanoDuration())

        return part1Result to part1Result
    }

    private fun collectAndSumMuls(slider: Slider): Int {
        var sum = 0
        var mChar = slider.jumpTo('m')
        while (mChar != null) {
            if (!slider.nextAndIsOnString("ul(")) {
                mChar = slider.jumpTo('m')
                continue
            }
            val (a, b) = slider.getNumbers()
            if (a == null || b == null) {
                mChar = slider.jumpTo('m')
                continue
            }
            if (!slider.nextAndIs(')')) {
                mChar = slider.jumpTo('m')
                continue
            }
            sum += a.toInt() * b.toInt()
            mChar = slider.jumpTo('m')
        }
        return sum
    }

    private fun Slider.nextAndIs(char: Char): Boolean {
        val next = next()
        return next == char
    }

    private fun Slider.nextAndIsOnString(string: String): Boolean {
        string.forEach {
            if (!nextAndIs(it)) {
                return false
            }
        }
        return true
    }

    private fun Slider.getNumber(): Int? {
        var c = this.next()
        var digits = ""
        while (c != null && c.isDigit()) {
            digits += c
            c = this.next()
        }
        this.previous()
        return digits.toIntOrNull()
    }

    private fun Slider.getNumbers(): Pair<Int?, Int?> {
        var a = this.getNumber() ?: return Pair(null, null)
        if (!this.nextAndIs(',')) {
            return Pair(a, null)
        }
        var b = this.getNumber() ?: return Pair(a, null)
        return Pair(a, b)
    }

}
