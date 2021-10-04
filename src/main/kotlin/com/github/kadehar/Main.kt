package com.github.kadehar

import com.github.kadehar.client.TelegramApi
import com.github.kadehar.config.Config
import com.github.kadehar.config.parseConfig
import com.github.kadehar.model.Summary
import com.github.kadehar.template.Message
import com.github.kadehar.utils.collectTemplateData
import com.github.kadehar.utils.prepareTemplateData
import com.github.kadehar.utils.templatePhrases
import com.github.kadehar.utils.toSummary
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response

fun main() {
    var config: Config
    runBlocking {
        config = parseConfig()
    }

    var summary: Summary
    var phrases: Map<String, String>
    var data: Map<String, Any>
    runBlocking {
        summary = toSummary(config.project.file)
        phrases = templatePhrases(config.project.language)
        data = prepareTemplateData(config.project, summary)
    }

    val map = collectTemplateData(phrases, data)
    val text = Message().createMessage(map)
    val client = TelegramApi.create().sendMessage(
        token = config.bot.token,
        chatId = config.bot.chatId,
        text = text,
        mode = "HTML"
    )

    client.enqueue(object: retrofit2.Callback<Response<String>> {
        override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
            if (response.body() != null) {
                println(response.body())
            }
        }

        override fun onFailure(call: Call<Response<String>>, t: Throwable) {
            println(t.message)
        }
    })
}