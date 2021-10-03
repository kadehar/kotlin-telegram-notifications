package com.github.kadehar.template

import freemarker.template.Configuration
import freemarker.template.Version
import java.io.StringWriter


class Message {
    private val encoding: String = "UTF-8"
    private val source: String = "/templates"
    private val version: Version = Configuration.VERSION_2_3_31

    fun createMessage(data: Map<String, Any>): String {
        val template = config(Message::class.java).getTemplate("tg.ftl")
        var text: String
        StringWriter().use { writer ->
            template.process(data, writer)
            text = writer.toString()
        }
        return text
    }

    private fun config(clazz: Class<*>?): Configuration {
        val config = Configuration(version)
        config.setClassForTemplateLoading(clazz, source)
        config.defaultEncoding = encoding
        return config
    }
}