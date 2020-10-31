package com.caster.notes.dsl.features.details.domain

import com.caster.notes.dsl.model.Note
import io.reactivex.Single
import javax.inject.Inject

interface NoteDetailsUseCase {
    fun saveNote(note: Note): Single<Long>
    fun delete(note: Note): Single<Unit>
}

class NoteDetailsUseCaseImpl @Inject constructor(
    private val repository: NoteDetailsRepository
) : NoteDetailsUseCase {
    override fun saveNote(note: Note): Single<Long> {
        return repository.saveNote(note)
    }

    override fun delete(note: Note): Single<Unit> {
        return repository.delete(note)
    }

}