package com.github.kadehar

import com.github.kadehar.template.Message
import com.github.kadehar.utils.collectTemplateData
import com.github.kadehar.utils.parseConfig
import com.github.kadehar.utils.toSummary

fun main() {
    val config = parseConfig()
    val summary = toSummary(config.project.file)
    val map = collectTemplateData(config.project, summary)
    println(Message().createMessage(map))
}