package com.github.kadehar

import com.github.kadehar.utils.msToTime
import com.github.kadehar.utils.parseConfig
import com.github.kadehar.utils.toSummary

fun main() {
    val config = parseConfig()
    val summary = toSummary(config.project.file)
    val time = msToTime(summary.time.duration)

    println(time)
    println(config.project.language)
}