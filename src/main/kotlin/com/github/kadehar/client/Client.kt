package com.github.kadehar.client

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface TelegramApi {
    @FormUrlEncoded
    @POST("bot{token}/sendMessage")
    fun sendMessage(
        @Path("token") token: String,
        @Field("chat_id") chatId: String,
        @Field("text") text: String,
        @Field("parse_mode") mode: String
    ): Call<Response<String>>

    companion object {
        private const val BASE_URL = "https://api.telegram.org"

        fun create(): TelegramApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(TelegramApi::class.java)
        }
    }
}