package com.example.healthcare.features.dash.di

import android.content.Context
import androidx.room.Room
import com.example.healthcare.features.dash.data.source.local.GlossaryDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGlossaryDB(@ApplicationContext context: Context): GlossaryDB {
        return Room.databaseBuilder(
            context,
            GlossaryDB::class.java,
            "glossary_database"
        ).build()
    }
}