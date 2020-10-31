package com.caster.notes.dsl.common.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.caster.notes.dsl.model.NotesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CoreDataModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        )
            .build()
    }
}