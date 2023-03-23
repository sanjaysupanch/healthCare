package com.example.healthcare.features.dash.data.source.remote

import com.example.healthcare.features.dash.data.model.Glossary
import retrofit2.http.GET

interface GlossaryApi {

    @GET("api/glossary.json")
    suspend fun getGlossaryData(): Glossary
}