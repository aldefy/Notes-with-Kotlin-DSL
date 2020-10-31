package com.caster.notes.dsl.features.list.domain

import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.BaseViewModel
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.observableIo
import com.caster.notes.dsl.common.singleIo
import javax.inject.Inject

class NotesListViewModel @Inject constructor(var useCase: NotesUseCase) : BaseViewModel() {
    private val _stateLiveData = MutableLiveData<NotesState>()
    val stateLiveData = _stateLiveData

    fun getNotes() {
        _stateLiveData.value = ShowLoading
        useCase.getNotes()
            .compose(observableIo())
            .subscribe({
                _stateLiveData.value = HideLoading
                if (it.isEmpty())
                    _stateLiveData.value = NotesEmpty
                else
                    _stateLiveData.value = NotesFetched(it)
            }, {
                _stateLiveData.value = HideLoading
            })
            .addTo(bag)
    }

    fun clearAll() {
        useCase.nuke()
            .compose(singleIo())
            .subscribe({
                _stateLiveData.value = NotesEmpty
            }, {
                _stateLiveData.value = ShowError(it)
            })
            .addTo(bag)
    }
}