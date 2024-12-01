package org.example.solution

import java.io.File

fun readFrom(path: String): List<String> = File(path).readLines()
