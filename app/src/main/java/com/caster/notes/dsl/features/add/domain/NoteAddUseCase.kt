package com.caster.notes.dsl.features.add.domain

import com.caster.notes.dsl.model.Note
import io.reactivex.Single
import javax.inject.Inject

interface NoteAddUseCase {
    fun insertNote(title: String, content: String): Single<Long>
}

class NoteAddUseCaseImpl @Inject constructor(
    private val repository: NoteAddRepository
) : NoteAddUseCase {
    override fun insertNote(title: String, content: String): Single<Long> {
        return repository.insertNote(
            Note(
                title = title,
                content = content,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }

}