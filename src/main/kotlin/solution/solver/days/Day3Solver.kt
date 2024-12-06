package org.example.solution.solver.days

import org.example.solution.solver.Solver
import kotlin.text.isDigit

// YES, this is NO REGEX solution!!!
class Day3Solver : Solver {

    data class Slider(private val value: List<String>) {

        private var index = 0
        private val chars = value.joinToString("").lowercase().toCharArray()

        fun next(): Char? {
            if (index >= chars.size) return null
            return chars[index++]
        }

        fun peek(): Char? {
            if (index >= chars.size) return null
            return chars[index]
        }

        fun previous() {
            index--
        }
    }

    interface InstructionResult
    data class MutInstructionResult(val sum: Int) : InstructionResult
    data class DoInstructionResult(val block: Boolean) : InstructionResult

    private companion object {
        private var isBlocked = false
    }

    override fun part1(input: List<String>) = runSolution(input)
    override fun part2(input: List<String>) = runSolution(input, allowBlocking = true)

    private fun runSolution(input: List<String>, allowBlocking: Boolean = false): Int {
        var sum = 0

        val slider = Slider(input)

        while (slider.peek() != null) {
            val instruction = slider.check(allowBlocking)
            when (instruction) {
                is MutInstructionResult -> sum += instruction.sum
                is DoInstructionResult -> isBlocked = instruction.block
                else -> {}
            }
        }

        return sum
    }

    private fun Slider.check(allowBlocking: Boolean = false): InstructionResult? {
        val c = this.next()!! // This is safe, because we're checking for null in main loop
        return when {
            c == 'm' && !isBlocked -> this.readMut()
            c == 'd' && allowBlocking -> this.readDo()
            else -> null
        }
    }

    private fun Slider.readMut(): MutInstructionResult? {
        if (!this.nextAndIsOnString("ul(")) return null
        val (a, b) = this.getNumbers()
        if (a == null || b == null) return null
        if (!this.nextAndIs(')')) return null
        return MutInstructionResult(a * b)
    }

    private fun Slider.readDo(): DoInstructionResult? {
        if (this.nextAndIsOnString("o()")) return DoInstructionResult(block = false)
        while (this.peek() != 'd') this.previous()
        this.next()
        if (this.nextAndIsOnString("on't()")) return DoInstructionResult(block = true)
        return null
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
