package com.caster.notes.dsl.features.details.domain

import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.NotesDatabase
import io.reactivex.Single
import javax.inject.Inject

interface NoteDetailsRepository {
    fun saveNote(note: Note): Single<Long>
}

class NoteDetailsRepositoryImpl @Inject constructor(
    private val db: NotesDatabase
) : NoteDetailsRepository {

    override fun saveNote(note: Note): Single<Long> {
        return Single.create { emitter ->
            if (emitter.isDisposed.not())
                emitter.onSuccess(
                    db.notesDao().addOrUpdate(note)
                )
            else
                emitter.onError(Throwable("Emitter disposed"))
        }
    }
}