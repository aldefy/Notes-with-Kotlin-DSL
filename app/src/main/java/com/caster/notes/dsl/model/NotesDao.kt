package com.caster.notes.dsl.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NotesDao {
    @Query("SELECT * FROM note ORDER BY createdAt DESC")
    fun getAllNotes(): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdate(note: Note) : Completable

    @Delete
    fun delete(note: Note) : Completable

    @Query("DELETE FROM note")
    fun nuke(): Completable
}