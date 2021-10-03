package com.github.kadehar

import com.github.kadehar.utils.msToTime
import com.github.kadehar.utils.toSummary

fun main() {
    val file = System.getProperty("file")
    val summary = toSummary(file)
    val time = msToTime(summary.time.duration)

    println(time)
}