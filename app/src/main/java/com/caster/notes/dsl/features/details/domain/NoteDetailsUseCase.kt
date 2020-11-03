package com.caster.notes.dsl.features.details.domain

import com.caster.notes.dsl.model.Note
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface NoteDetailsUseCase {
    fun saveNote(note: Note): Completable
    fun delete(note: Note): Completable
}

class NoteDetailsUseCaseImpl @Inject constructor(
    private val repository: NoteDetailsRepository
) : NoteDetailsUseCase {
    override fun saveNote(note: Note): Completable {
        return repository.saveNote(note)
    }

    override fun delete(note: Note): Completable {
        return repository.delete(note)
    }

}