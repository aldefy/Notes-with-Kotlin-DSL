package com.caster.notes.dsl.features.list.domain

import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.NotesDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface NoteRepository {
    fun getNotes(): Observable<List<Note>>
    fun insertNote(note: Note): Completable
    fun nuke(): Completable
}

class NoteRepositoryImpl @Inject constructor(
    private val db: NotesDatabase
) : NoteRepository {
    override fun getNotes(): Observable<List<Note>> {
        return db.notesDao().getAllNotes()
    }

    override fun insertNote(note: Note): Completable {
        return db.notesDao().addOrUpdate(note)
    }

    override fun nuke(): Completable {
        return db.notesDao().nuke()
    }
}