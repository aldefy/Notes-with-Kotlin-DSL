package com.caster.notes.dsl.features.details.domain

import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.BaseViewModel
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.completableIo
import com.caster.notes.dsl.common.singleIo
import com.caster.notes.dsl.model.Note
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(var useCase: NoteDetailsUseCase) : BaseViewModel() {
    private val _stateLiveData = MutableLiveData<NoteDetailsState>()
    val stateLiveData = _stateLiveData

    fun saveNote(note: Note) {
        _stateLiveData.value = ShowLoading
        useCase.saveNote(note)
            .compose(completableIo())
            .subscribe({
                _stateLiveData.value = HideLoading
                _stateLiveData.value = NoteSaved
            }, {
                _stateLiveData.value = NoteSaveFailure(it)
                _stateLiveData.value = HideLoading
            })
            .addTo(bag)
    }

    fun deleteNote(note: Note) {
        useCase.delete(note)
            .compose(completableIo())
            .subscribe({
                _stateLiveData.value = NoteRemoved
            }, {
                it.printStackTrace()
            })
            .addTo(bag)
    }

}