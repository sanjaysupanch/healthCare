package com.example.healthcare.features.common.service

import com.example.healthcare.features.dash.data.source.remote.GlossaryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GlossaryRetrofit {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val glossaryApi: GlossaryApi by lazy {
        retrofit.create(GlossaryApi::class.java)
    }
}