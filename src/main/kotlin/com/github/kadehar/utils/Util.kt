package com.github.kadehar.utils

import com.github.kadehar.model.Summary
import com.google.gson.GsonBuilder
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*

fun toSummary(file: String): Summary = GsonBuilder()
    .setPrettyPrinting()
    .create()
    .fromJson(FileReader(file), Summary::class.java)

fun msToTime(duration: Long): String {
    val formatter = SimpleDateFormat("HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(duration))
}