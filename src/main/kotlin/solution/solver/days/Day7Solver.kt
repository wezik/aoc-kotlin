package org.example.solution.solver.days

import org.example.solution.solver.Solver

class Day7Solver : Solver {

    private val combinationCache = mutableMapOf<Pair<List<String>, Int>, List<List<String>>>()
    private val validationCache = mutableMapOf<Pair<Equation, Boolean>, Boolean>()

    private fun interface Instruction {
        fun value(): Long
        operator fun plus(other: Instruction) = value() + other.value()
        operator fun times(other: Instruction) = value() * other.value()
    }

    private data class Equation(val sum: Long, val values: List<Long>)

    private fun List<String>.parse(): List<Equation> {
        return this.map { it.split(":") }.map {
            Equation(sum = it.first().toLong(),
                values = it.last().split(" ").filter { it.isNotBlank() }.map { it.toLong() })
        }
    }

    // Generate all possible combinations for a given list and length (with caching)
    fun generateCombinations(strings: List<String>, length: Int): List<List<String>> {
        val key = strings to length
        if (key in combinationCache) return combinationCache[key]!!

        if (length == 0) {
            combinationCache[key] = listOf(emptyList())
            return combinationCache[key]!!
        }

        val smallerCombinations = generateCombinations(strings, length - 1)
        val result = smallerCombinations.flatMap { combo ->
            strings.map { str -> combo + str }
        }

        combinationCache[key] = result
        return result
    }

    private fun Equation.isValid(allowJoined: Boolean = false): Boolean {
        val key = this to allowJoined
        if (key in validationCache) return validationCache[key]!!

        val validOperators = mutableListOf("+", "*")
        if (allowJoined) validOperators.add("|")

        val operatorCombos = generateCombinations(validOperators, values.size - 1)
        for (combo in operatorCombos) {
            if (this.toInstructionOf(combo).value() == sum) {
                validationCache[key] = true
                return true
            }
        }

        validationCache[key] = false
        return false
    }

    private fun createInstruction(op: String, a: Instruction, b: Instruction): Instruction {
        return when (op) {
            "+" -> Instruction { a + b }
            "*" -> Instruction { a * b }
            "|" -> Instruction { "${a.value()}${b.value()}".toLong() }
            else -> throw IllegalArgumentException("Unknown operator $op")
        }
    }

    private fun Equation.toInstructionOf(operators: List<String>): Instruction {
        var instruction = createInstruction(
            operators.first(), Instruction { values.first() }, Instruction { values[1] }
        )

        for (i in 2 until values.size) {
            instruction = createInstruction(operators[i - 1], instruction, Instruction { values[i] })
        }

        return instruction
    }

    override fun part1(input: List<String>): String {
        val equations = input.parse()
        return equations.filter { it.isValid() }.map { it.sum }.sum().toString()
    }

    override fun part2(input: List<String>): String {
        val equations = input.parse()
        return equations.filter { it.isValid(allowJoined = true) }.map { it.sum }.sum().toString()
    }
}
