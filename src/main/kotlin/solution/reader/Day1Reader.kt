package org.example.solution.reader

import org.example.solution.InputReader
import java.io.File

class Day1Reader : InputReader {
    override fun read(path: String): List<String> {
        return File(path).readLines()
    }
}
