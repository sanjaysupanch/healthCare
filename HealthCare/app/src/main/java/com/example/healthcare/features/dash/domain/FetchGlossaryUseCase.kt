package com.example.healthcare.features.dash.domain

import com.example.healthcare.features.common.service.GlossaryRetrofit
import com.example.healthcare.features.dash.data.model.Glossary
import com.example.healthcare.features.dash.data.model.GlossaryItem
import com.example.healthcare.features.dash.data.source.local.GlossaryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchGlossaryUseCase @Inject constructor(
    private val glossaryDB: GlossaryDB
) {
    fun getGlossary(): Flow<Glossary> = flow {
        var glossaryList = convertToGlossary(glossaryDB.glossaryDao().getAllGlossaries())

        if (glossaryList.glossary.isEmpty()) {
            glossaryList = GlossaryRetrofit.glossaryApi.getGlossaryData()
            glossaryDB.glossaryDao().insertGlossary(glossaryList.glossary)
        }
        emit(glossaryList)
    }.flowOn(Dispatchers.IO)

    private fun convertToGlossary(items: List<GlossaryItem>): Glossary {
        return Glossary(items)
    }
}
