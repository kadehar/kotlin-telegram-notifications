package com.github.kadehar.async

import com.github.kadehar.config.Config
import com.github.kadehar.config.Language
import com.github.kadehar.config.Project
import com.github.kadehar.model.Summary
import com.github.kadehar.utils.parseConfig
import com.github.kadehar.utils.prepareTemplateData
import com.github.kadehar.utils.templatePhrases
import com.github.kadehar.utils.toSummary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun parseConfigAsync(): Config = CoroutineScope(Dispatchers.IO).async {
    return@async parseConfig()
}.await()

suspend fun parseSummaryAsync(file: String): Summary = CoroutineScope(Dispatchers.IO).async {
    return@async toSummary(file)
}.await()

suspend fun collectPhrasesAsync(language: Language): Map<String, String> = CoroutineScope(Dispatchers.IO).async {
    return@async templatePhrases(language)
}.await()

suspend fun collectDataAsync(project: Project, summary: Summary): Map<String, Any> =
    CoroutineScope(Dispatchers.IO).async {
        return@async prepareTemplateData(project, summary)
    }.await()
