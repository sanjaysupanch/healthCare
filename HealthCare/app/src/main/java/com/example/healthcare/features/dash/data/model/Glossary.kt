package com.example.healthcare.features.dash.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Glossary(
    val glossary: List<GlossaryItem>
) {
    constructor() : this(emptyList())
}

@Entity(tableName = "glossaries")
data class GlossaryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String,
    val date: String,
    val title: String,
    val url: String
)


