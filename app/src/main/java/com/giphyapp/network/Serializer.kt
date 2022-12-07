package com.giphyapp.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID

object Serializer {
    @JvmStatic
    val gsonBuilder: GsonBuilder = GsonBuilder()


    @JvmStatic
    val gson: Gson by lazy {
        gsonBuilder.create()
    }
}
