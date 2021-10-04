package com.github.kadehar.utils

import com.github.kadehar.config.Language
import com.github.kadehar.config.Project
import com.github.kadehar.model.Summary
import com.github.kadehar.template.Phrases
import com.uchuhimo.konf.toValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import com.uchuhimo.konf.Config as conf

suspend fun parseLocalizedFile(file: String): Phrases = CoroutineScope(Dispatchers.IO).async {
    conf().from.json.file(file).toValue<Phrases>()
}.await()

suspend fun templatePhrases(language: Language): Map<String, String> = CoroutineScope(Dispatchers.IO).async {
    val phrases = localize(language)
    mapOf(
        "projectPhrase" to phrases.project,
        "durationPhrase" to phrases.duration,
        "totalScenariosPhrase" to phrases.totalScenarios,
        "passedPhrase" to phrases.passed,
        "failedPhrase" to phrases.failed,
        "reportPhrase" to phrases.report
    )
}.await()

suspend fun prepareTemplateData(project: Project, summary: Summary): Map<String, Any> =
    CoroutineScope(Dispatchers.IO).async {
        mapOf(
            "project" to project.name,
            "duration" to msToTime(summary.time.duration),
            "totalScenarios" to summary.statistic.total,
            "passed" to summary.statistic.passed,
            "failed" to summary.statistic.failed,
            "report" to project.link
        )
    }.await()

fun collectTemplateData(phrases: Map<String, String>, data: Map<String, Any>): Map<String, Any> {
    val map = hashMapOf<String, Any>()
    map.putAll(phrases)
    map.putAll(data)
    return map
}

private suspend fun localize(language: Language): Phrases = CoroutineScope(Dispatchers.IO).async {
    when (language) {
        Language.RU -> parseLocalizedFile("src/main/resources/strings/ru.json")
        Language.EN -> parseLocalizedFile("src/main/resources/strings/en.json")
    }
}.await()
