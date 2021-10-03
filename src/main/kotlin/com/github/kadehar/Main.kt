package com.github.kadehar

import com.github.kadehar.client.TelegramApi
import com.github.kadehar.template.Message
import com.github.kadehar.utils.collectTemplateData
import com.github.kadehar.utils.parseConfig
import com.github.kadehar.utils.toSummary
import retrofit2.Call
import retrofit2.Response

fun main() {
    val config = parseConfig()
    val summary = toSummary(config.project.file)
    val map = collectTemplateData(config.project, summary)
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