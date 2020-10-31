package com.caster.notes.dsl.features.list.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.BaseViewModel
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.observableIo
import com.caster.notes.dsl.common.singleIo
import com.caster.notes.dsl.model.Note
import javax.inject.Inject

class NotesListViewModel @Inject constructor(var useCase: NotesUseCase) : BaseViewModel() {
    private val _stateLiveData = MutableLiveData<NotesState>()
    val stateLiveData = _stateLiveData

    fun insertNote(note: Note) {
        useCase.insertNote(note)
            .compose(singleIo())
            .subscribe({
                Log.d("Notes", "$it")
            }, {
                it.printStackTrace()
            })
            .addTo(bag)
    }

    fun getNotes() {
        _stateLiveData.value = ShowLoading
        useCase.getNotes()
            .compose(observableIo())
            .subscribe({
                _stateLiveData.value = HideLoading
                _stateLiveData.value = NotesFetched(it)
            }, {
                _stateLiveData.value = HideLoading
            })
            .addTo(bag)
    }
}