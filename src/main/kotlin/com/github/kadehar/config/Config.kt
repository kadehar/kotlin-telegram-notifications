package com.github.kadehar.config

import com.uchuhimo.konf.toValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import com.uchuhimo.konf.Config as conf

data class Bot(val token: String, val chatId: String)
data class Project(
    val name: String,
    val file: String,
    val language: Language,
    val link: String
)
data class Config(val bot: Bot, val project: Project)

enum class Language {
    RU, EN
}

suspend fun parseConfig(): Config = CoroutineScope(Dispatchers.IO).async {
    val config = System.getProperty("config")
    conf().from.json.file(config).toValue<Config>()
}.await()