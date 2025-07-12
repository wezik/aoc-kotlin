package app.wezik.aoc.domain

import java.time.LocalDateTime
import java.time.ZoneId
import com.github.ajalt.clikt.core.UsageError

@JvmInline
value class Day(val value: Int)

@JvmInline
value class Year(val value: Int) {

    companion object {
        // NOTE: resolves to most up-to-date valid year of advent of code
        fun recent(): Year {
            // NOTE: advent of code launches at midnight EST time (1st day of December)
            val estZoneId = ZoneId.of("America/New_York")
            val zonedDateTime = LocalDateTime.now(estZoneId)
            return Year(if (zonedDateTime.monthValue == 12) zonedDateTime.year else zonedDateTime.year - 1)
        }
    }

}
