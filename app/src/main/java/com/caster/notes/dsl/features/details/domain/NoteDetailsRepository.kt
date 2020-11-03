package com.caster.notes.dsl.features.details.domain

import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.NotesDatabase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface NoteDetailsRepository {
    fun saveNote(note: Note): Completable
    fun delete(note: Note): Completable
}

class NoteDetailsRepositoryImpl @Inject constructor(
    private val db: NotesDatabase
) : NoteDetailsRepository {

    override fun saveNote(note: Note): Completable {
        return db.notesDao().addOrUpdate(note)
    }

    override fun delete(note: Note): Completable {
        return db.notesDao().delete(note)
    }
}