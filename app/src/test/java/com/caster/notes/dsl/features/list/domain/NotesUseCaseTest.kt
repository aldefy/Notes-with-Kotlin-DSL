package com.caster.notes.dsl.features.list.domain

import com.caster.notes.dsl.common.test.BaseJUnitTest
import com.caster.notes.dsl.common.test.willReturn
import com.caster.notes.dsl.model.Note
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class NotesUseCaseTest : BaseJUnitTest() {

    private val repository = mock<NoteRepository>()
    private lateinit var useCase: NotesUseCase

    override fun start() {
        useCase = NotesUseCaseImpl(
            repository
        )
    }

    override fun stop() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should emit list of notes when data source fetch is a success with atleast one result`() {
        val notes = mutableListOf<Note>()
            .also {
                it.add(Note(
                    title = "",
                    content = "",
                    createdAt = System.currentTimeMillis()
                ))
            }
        repository.getNotes() willReturn Observable.fromCallable {
            notes
        }
        useCase.getNotes().test().assertOf {
            val results = it.values().last()
            assert(results.isNotEmpty())
        }

        verify(repository).getNotes()
    }

    @Test
    fun `should emit list of notes when data source fetch is a success with zero result`() {
        val notes = mutableListOf<Note>()
        repository.getNotes() willReturn Observable.fromCallable {
            notes
        }
        useCase.getNotes().test().assertOf {
            val results = it.values().last()
            assert(results.isEmpty())
        }

        verify(repository).getNotes()
    }
}