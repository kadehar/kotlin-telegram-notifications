package com.github.kadehar.utils

import com.github.kadehar.model.Summary
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*

suspend fun toSummary(file: String): Summary = CoroutineScope(Dispatchers.IO).async {
    GsonBuilder()
        .setPrettyPrinting()
        .create()
        .fromJson(FileReader(file), Summary::class.java)
}.await()

fun msToTime(duration: Long): String {
    val formatter = SimpleDateFormat("HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(duration))
}