package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver
import java.util.PriorityQueue

class Day24 : Solver {
    
    override fun part1(input: List<String>): String {
        val variables = mutableMapOf<String, Int>()
        var blankLine = 0
        for (line in input) {
            if (line.isBlank()) {
                break
            }
            val (variable, value) = line.split(": ")
            variables[variable] = value.toInt()
            blankLine++
        }

        var i = 0
        val ops = input.subList(blankLine + 1, input.size).map { it.split(" -> ") }.map { out ->
            val (a, b) = out
            val (x, op, y) = a.split(" ")
            Triple(Triple(x, y, i), op, b).also { i++ }
        }

        val queue = PriorityQueue<Triple<Triple<String, String, Int>, String, String>>(compareBy { 
            val (a, b, order) = it.first
            val hasOps = variables.containsKey(a) && variables.containsKey(b)
            if (hasOps) order else 1000000 + order
        })
        queue += ops
        while (queue.isNotEmpty()) {
            val (currentVariables, op, result) = queue.poll()
            println("$currentVariables -> $op -> $result")
            val (x, y) = currentVariables.let { (x, y) -> variables[x]!! to variables[y]!! }
            val resultValue = when (op) {
                "XOR" -> x xor y
                "AND" -> x and y
                "OR" -> x or y
                else -> error("Unknown operator: $op")
            }
            variables[result] = resultValue

            // updating entire queue order
            val tempList = queue.toList()
            queue.clear()
            queue += tempList
        }
    
        var resultBinary = variables.entries
            .filter { it.key[1].isDigit() && it.key[0] == 'z' }
            .sortedBy { it.key }
            .reversed()
            .joinToString("") { "${it.value}" }

        return resultBinary.toLong(2).toString()
    }
    override fun part2(input: List<String>) = ""

}
