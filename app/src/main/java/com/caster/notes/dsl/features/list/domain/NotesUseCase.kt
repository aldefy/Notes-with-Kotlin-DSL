package com.caster.notes.dsl.features.list.domain

import com.caster.notes.dsl.model.Note
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface NotesUseCase {
    fun getNotes() : Observable<List<Note>>
}

class NotesUseCaseImpl @Inject constructor(
    private val repository: NoteRepository
) : NotesUseCase {

    override fun getNotes(): Observable<List<Note>> {
        return repository.getNotes()
    }
}