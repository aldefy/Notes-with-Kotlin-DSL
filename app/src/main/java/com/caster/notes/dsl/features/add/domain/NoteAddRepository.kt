package com.caster.notes.dsl.features.add.domain

import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.NotesDatabase
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface NoteAddRepository {
    fun insertNote(note: Note): Single<Long>
}

class NoteAddRepositoryImpl @Inject constructor(
    private val db: NotesDatabase
) : NoteAddRepository {

    override fun insertNote(note: Note): Single<Long> {
        return Single.create { emitter ->
            if (emitter.isDisposed.not())
                emitter.onSuccess(
                    db.notesDao().insert(note)
                )
            else
                emitter.onError(Throwable("Emitter disposed"))
        }
    }
}