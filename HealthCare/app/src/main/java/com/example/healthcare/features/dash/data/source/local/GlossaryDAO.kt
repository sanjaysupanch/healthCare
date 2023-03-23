package com.example.healthcare.features.dash.data.source.local

import androidx.room.*
import com.example.healthcare.features.dash.data.model.GlossaryItem

@Dao
interface GlossaryDao {

    @Query("SELECT * FROM glossaries")
    suspend fun getAllGlossaries(): List<GlossaryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlossary(glossary: List<GlossaryItem>)
}
