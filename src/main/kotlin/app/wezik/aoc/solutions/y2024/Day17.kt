package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import java.io.File

class Day17 : Day {

    private fun runProgram(program: List<Int>, a: Long): List<Int> {
        var a = a
        var b = 0L
        var c = 0L

        fun combo(operand: Int): Long {
            return when (operand) {
                in 0..3 -> operand.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> error("Invalid operand: $operand")
            }
        }

        var pointer = 0
        val output = mutableListOf<Int>()
        while (pointer < program.size - 1) {
            val opcode = program[pointer]
            val operand = program[pointer + 1]
            when (opcode) {
                0 -> a = a shr combo(operand).toInt()         // adv
                1 -> b = b xor operand.toLong()               // bxl
                2 -> b = combo(operand) % 8                   // bst
                3 -> if (a != 0L) pointer = operand - 2       // jnz
                4 -> b = b xor c                              // bxc
                5 -> output.add((combo(operand) % 8).toInt()) // out
                6 -> b = a shr combo(operand).toInt()         // bdv
                7 -> c = a shr combo(operand).toInt()         // cdv
            }
            pointer += 2
        }
        return output
    }

    override fun part1(input: File): String {
        val input = input.readLines()
        var a = input[0].split(": ")[1].toLong()
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }
        return runProgram(program, a).joinToString(",")
    }

    override fun part2(input: File): String {
        val input = input.readLines()
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }
        // Recursively reconstructs the correct input for register A
        // Checks values in reversed order calculating A range and then shifting 3 bits left for next recursion
        // Last instruction is a conditional jump (jnz) that skips only on 0 so it has to end with 0
        fun find(answer: Long = 0, offset: Int = 0): Long? {
            if (offset >= program.size) return answer
            // Limit 0..7 range since we are operating on 3 bits
            for (i in 0..7) {
                // shift A register by 3 seems to be an instruction that appears in every input
                // and also it's the only one affecting register A
                var a = (answer shl 3) + i
                val result = runProgram(program, a)
                if (result.size - offset - 1 < 0) continue

                if (result[result.size - offset - 1] == program[program.size - offset - 1]) {
                    // first match isn't necessarily the one we are looking for, so we continue on fail
                    val candidate = find(a, offset + 1) ?: continue
                    return candidate
                }
            }
            return null
        }
        return find().toString()
    }

}
