package com.caster.notes.dsl.features.add.presentation

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.bind
import com.caster.notes.dsl.features.add.domain.NoteAddState
import com.caster.notes.dsl.features.add.domain.NoteSaved
import com.caster.notes.dsl.features.list.domain.HideLoading
import com.caster.notes.dsl.features.list.domain.ShowLoading
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface NoteAddScreen : LifecycleObserver {
    val event: LiveData<NoteAddEvent>

    fun bind(
        view: NoteAddView,
        observable: Observable<NoteAddState>
    ): Disposable
}

class NoteAddScreenImpl : NoteAddScreen {
    private val _event = MutableLiveData<NoteAddEvent>()
    override val event: LiveData<NoteAddEvent> = _event

    override fun bind(view: NoteAddView, observable: Observable<NoteAddState>): Disposable {
        return CompositeDisposable().apply {
            setupContent(view, observable, this)
            setupLoading(view, observable, this)
            setupError(view, observable, this)
        }
    }

    fun withSaveButtonClick(view: NoteAddView) {
        view.saveButton.setOnClickListener {
            val title = view.getTitle()
            val content = view.getContent()
            _event.value = SubmitClicked(title, content)
        }
    }

    fun withInputActionDone(view: NoteAddView){
        view.contentET.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                view.saveButton.callOnClick()
                true
            }
            false
        }
    }

    private fun setupLoading(
        view: NoteAddView,
        observable: Observable<NoteAddState>,
        compositeDisposable: CompositeDisposable
    ) {
        observable
            .ofType(ShowLoading::class.java)
            .map { Unit }
            .bind(view.showLoading())
            .addTo(compositeDisposable)

        observable
            .ofType(HideLoading::class.java)
            .map { Unit }
            .bind(view.hideLoading())
            .addTo(compositeDisposable)
    }

    private fun setupContent(
        view: NoteAddView,
        observable: Observable<NoteAddState>,
        compositeDisposable: CompositeDisposable
    ) {
        observable
            .ofType(NoteSaved::class.java)
            .map {
                Unit
                _event.value = AddNoteSuccess
            }
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun setupError(
        view: NoteAddView,
        observable: Observable<NoteAddState>,
        compositeDisposable: CompositeDisposable
    ) {

    }
}