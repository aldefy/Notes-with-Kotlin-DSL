package com.caster.notes.dsl.model

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NotesDao {
    @Query("SELECT * FROM note ORDER BY createdAt DESC")
    fun getAllNotes(): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note) : Long

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note")
    fun deleteAll()
}