package com.caster.notes.dsl.features.details.domain

import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.BaseViewModel
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.singleIo
import com.caster.notes.dsl.model.Note
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(var useCase: NoteDetailsUseCase) : BaseViewModel() {
    private val _stateLiveData = MutableLiveData<NoteDetailsState>()
    val stateLiveData = _stateLiveData

    fun saveNote(note: Note) {
        _stateLiveData.value = ShowLoading
        useCase.saveNote(note)
            .compose(singleIo())
            .subscribe({
                _stateLiveData.value = HideLoading
                _stateLiveData.value = NoteSaved
            }, {
                it.printStackTrace()
                _stateLiveData.value = HideLoading
            })
            .addTo(bag)
    }

}