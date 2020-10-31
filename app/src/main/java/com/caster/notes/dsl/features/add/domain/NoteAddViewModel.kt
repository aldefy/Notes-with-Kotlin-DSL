package com.caster.notes.dsl.features.add.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.BaseViewModel
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.singleIo
import javax.inject.Inject

class NoteAddViewModel @Inject constructor(var useCase: NoteAddUseCase) : BaseViewModel() {
    private val _stateLiveData = MutableLiveData<NoteAddState>()
    val stateLiveData = _stateLiveData

    fun insertNote(title: String, content: String) {
        _stateLiveData.value = ShowLoading
        useCase.insertNote(title, content)
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