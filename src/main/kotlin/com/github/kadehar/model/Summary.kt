package com.github.kadehar.model


data class Summary(val statistic: Statistic, val time: Time)

data class Statistic(
    val total: Int,
    val failed: Int,
    val passed: Int
)

data class Time(val duration: Long)