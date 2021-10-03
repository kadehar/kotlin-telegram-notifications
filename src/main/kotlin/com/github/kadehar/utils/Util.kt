package com.github.kadehar.utils

import com.github.kadehar.config.Config
import com.github.kadehar.config.Language
import com.github.kadehar.config.Project
import com.github.kadehar.model.Summary
import com.github.kadehar.template.Phrases
import com.google.gson.GsonBuilder
import com.uchuhimo.konf.toValue
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*
import com.uchuhimo.konf.Config as conf

fun toSummary(file: String): Summary = GsonBuilder()
    .setPrettyPrinting()
    .create()
    .fromJson(FileReader(file), Summary::class.java)

fun msToTime(duration: Long): String {
    val formatter = SimpleDateFormat("HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(duration))
}

fun parseConfig(): Config {
    val config = System.getProperty("config")
    return conf().from.json.file(config).toValue()
}

fun localize(language: Language): Phrases =
    when(language) {
        Language.RU -> parseLocalizedFile("src/main/resources/strings/ru.json")
        Language.EN -> parseLocalizedFile("src/main/resources/strings/en.json")
    }

fun parseLocalizedFile(file: String): Phrases {
    return conf().from.json.file(file).toValue()
}

fun templatePhrases(language: Language): Map<String, String> {
    val phrases = localize(language)
    return mapOf(
        "projectPhrase" to phrases.project,
        "durationPhrase" to phrases.duration,
        "totalScenariosPhrase" to phrases.totalScenarios,
        "passedPhrase" to phrases.passed,
        "failedPhrase" to phrases.failed,
        "reportPhrase" to phrases.report
    )
}

fun prepareTemplateData(project: Project, summary: Summary): Map<String, Any> {
    return mapOf(
        "project" to project.name,
        "duration" to msToTime(summary.time.duration),
        "totalScenarios" to summary.statistic.total,
        "passed" to summary.statistic.passed,
        "failed" to summary.statistic.failed,
        "report" to project.link
    )
}

fun collectTemplateData(project: Project, summary: Summary): Map<String, Any> {
    val map = hashMapOf<String, Any>()
    map.putAll(templatePhrases(project.language))
    map.putAll(prepareTemplateData(project, summary))
    return map
}