package com.github.kadehar.config

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