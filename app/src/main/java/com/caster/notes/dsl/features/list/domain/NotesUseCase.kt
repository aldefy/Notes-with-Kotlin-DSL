package com.caster.notes.dsl.features.list.domain

import com.caster.notes.dsl.common.NotesOperationError
import com.caster.notes.dsl.model.Note
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface NotesUseCase {
    fun getNotes() : Observable<List<Note>>
    fun nuke(): Completable
}

class NotesUseCaseImpl @Inject constructor(
    private val repository: NoteRepository
) : NotesUseCase {

    override fun getNotes(): Observable<List<Note>> {
        return repository.getNotes()
    }

    override fun nuke(): Completable {
        return repository.nuke()
            .doOnError {
                throw NotesOperationError()
            }
    }
}