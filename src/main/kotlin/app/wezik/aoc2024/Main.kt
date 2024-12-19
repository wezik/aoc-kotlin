package app.wezik.aoc2024

import app.wezik.aoc2024.utils.time
import app.wezik.aoc2024.utils.toNanoDuration
import java.io.File
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.DurationUnit

private fun Duration.formatToSeconds() = this.toString(DurationUnit.SECONDS, 3).replace("s", " s")
private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3).replace("ms", " ms")

fun main(args: Array<String>) {
    val config = Config(args)

    //@formatter:off
    fun logln(message: String) = if (!config.quietMode) println(message) else {}
    fun log(message: String) = if (!config.quietMode) print(message) else {}
    fun logAnswer(message: String) = if (config.quietMode) println(message) else {}
    //@formatter:on

    for ((source, inputPath) in config.solversToRun) {
        val file = File(inputPath)
        if (!file.exists()) {
            error("File does not exist: $inputPath")
        }

        val input = file.readLines() // TODO change to plain string

        // Default run
        val day = if (source.ordinal < 9) "0${source.ordinal + 1}" else source.ordinal + 1
        log("Day $day:")
        when (config.runMode) {
            RunMode.DEFAULT -> {
                // part 1
                var p1Solution = ""
                val p1Time = time {
                    p1Solution = source.solver.part1(input)
                }.toNanoDuration().formatToSeconds()
                logln("\u001b[J Part 1 ($p1Time): $p1Solution")

                // part 2
                var p2Solution = ""
                val p2Time = time {
                    p2Solution = source.solver.part2(input)
                }.toNanoDuration().formatToSeconds()
                logln("        Part 2 ($p2Time): $p2Solution")

                if (config.format) {
                    config.formatter.appendSolution("Day $day", "$p1Solution ($p1Time)", "$p2Solution ($p2Time)")
                }

                // I am neither using nor have an idea for quiet answer to a regular solution mode
                // There is also an issue that I use commas to separate values and day 17 part 1 uses commas in the output
                // logAnswer("Day ${source.ordinal + 1},${p1Solution},${p2Solution},${p1Time},${p2Time}")
            }

            RunMode.BENCHMARK -> {

                val testCount = if (config.overrideLimits) {
                    config.benchmarkRuns
                } else {
                    min(config.benchmarkRuns, config.getRunLimit(source))
                }

                val start = System.nanoTime()
                val warmup = 5

                // warmup part 1
                log("\r\u001b[JDay $day Part 1 warming up...")
                repeat(warmup) {
                    source.solver.part1(input)
                }

                // part 1
                log("\r\u001b[JDay $day Part 1 running...")
                val p1Times = mutableListOf<Long>()
                repeat(testCount) {
                    val p1Time = time {
                        source.solver.part1(input)
                    }
                    p1Times.add(p1Time)
                }
                val p1Time = p1Times.average().toLong().toNanoDuration().formatToMs()
                logln("\r\u001b[JDay $day Part 1 average: $p1Time")

                // warmup part 2
                log("       Part 2 warming up...")
                repeat(warmup) {
                    source.solver.part2(input)
                }

                // part 2
                log("\r\u001b[J       Part 2 running...")
                val p2Times = mutableListOf<Long>()
                repeat(testCount) {
                    val p2Time = time {
                        source.solver.part2(input)
                    }
                    p2Times.add(p2Time)
                }
                val p2Time = p2Times.average().toLong().toNanoDuration().formatToMs()
                logln("\r\u001b[J       Part 2 average: $p2Time")

                // summary
                logln("       Total runs: $testCount")
                val totalTime = (System.nanoTime() - start).toNanoDuration().formatToSeconds()
                logln("       Total time: $totalTime")

                if (config.format) {
                    config.formatter.appendBenchmark("Day $day", p1Time, p2Time, testCount, totalTime)
                }

                logAnswer("Day ${source.ordinal + 1},${p1Time},${p2Time},$testCount,$totalTime")
            }

        }

    }
    if (config.format) {
        logln("Formatting results...")
        config.formatter.build("results")
    }

}
