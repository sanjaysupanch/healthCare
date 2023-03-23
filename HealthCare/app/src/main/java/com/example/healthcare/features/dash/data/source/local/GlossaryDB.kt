package com.example.healthcare.features.dash.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthcare.features.dash.data.model.GlossaryItem

@Database(entities = [GlossaryItem::class], version = 1)
abstract class GlossaryDB : RoomDatabase() {
    abstract fun glossaryDao(): GlossaryDao
}
