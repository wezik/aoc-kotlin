package org.example.solution

import kotlin.time.Duration

interface InputSolver {
    fun solve(input: List<String>): Pair<Result, Result>
}

data class Result(val value: String, val time: Duration)
