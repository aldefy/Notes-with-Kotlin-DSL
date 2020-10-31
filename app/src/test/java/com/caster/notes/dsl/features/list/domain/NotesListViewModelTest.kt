package com.caster.notes.dsl.features.list.domain

import androidx.lifecycle.Observer
import com.caster.notes.dsl.common.test.BaseJUnitTest
import com.caster.notes.dsl.common.test.willReturn
import com.caster.notes.dsl.common.test.willReturnError
import com.caster.notes.dsl.model.Note
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotesListViewModelTest : BaseJUnitTest() {

    private val useCase = mock<NotesUseCase>()
    private val observer = mock<Observer<NotesState>>()
    private lateinit var vm: NotesListViewModel

    override fun start() {
        vm = NotesListViewModel(
            useCase = useCase
        )
    }

    override fun stop() {
        verifyNoMoreInteractions(useCase)
    }

    @Test
    fun `getNotes should show empty list when data source is empty`() {
        val notes = mutableListOf<Note>()
        useCase.getNotes() willReturn Observable.fromCallable {
            notes
        }
        vm.stateLiveData.observeForever(observer)
        vm.getNotes()
        verify(observer).onChanged(ShowLoading)
        verify(observer).onChanged(NotesEmpty)
        verify(observer).onChanged(HideLoading)
        verify(useCase).getNotes()

    }

    @Test
    fun `getNotes should show items in list when data source has atleast one entry`() {
        val notes = mutableListOf<Note>(
            Note(
                title = "",
                content = "",
                createdAt = System.currentTimeMillis()
            )
        )
        useCase.getNotes() willReturn Observable.fromCallable {
            notes
        }
        vm.stateLiveData.observeForever(observer)
        vm.getNotes()
        verify(observer).onChanged(ShowLoading)
        verify(observer).onChanged(NotesFetched(notes))
        verify(observer).onChanged(HideLoading)
        verify(useCase).getNotes()
    }

    @Test
    fun `getNotes should emit error when data source throws an throwable`() {
        val throwable = Throwable()
        useCase.getNotes() willReturnError throwable
        vm.stateLiveData.observeForever(observer)
        vm.getNotes()
        verify(observer).onChanged(ShowLoading)
        verify(observer).onChanged(ShowError(throwable))
        verify(observer).onChanged(HideLoading)
        verify(useCase).getNotes()
    }

    @Test
    fun `clearAll should clear entire list and show empty`() {
        useCase.nuke() willReturn Single.fromCallable { Unit }
        vm.stateLiveData.observeForever(observer)
        vm.clearAll()
        verify(observer).onChanged(NotesEmpty)
        verify(useCase).nuke()
    }

    @Test
    fun `clearAll should emit error when data source cant nuke data`() {
        val throwable = Throwable()
        useCase.nuke() willReturnError throwable
        vm.stateLiveData.observeForever(observer)
        vm.clearAll()
        verify(observer).onChanged(ShowError(throwable))
        verify(useCase).nuke()
    }
}