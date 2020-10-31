package com.caster.notes.dsl.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val bag by lazy { CompositeDisposable() }

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }
}