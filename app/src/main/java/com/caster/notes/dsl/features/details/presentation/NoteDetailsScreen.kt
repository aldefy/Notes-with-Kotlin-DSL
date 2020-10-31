package com.caster.notes.dsl.features.details.presentation

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.bind
import com.caster.notes.dsl.features.details.domain.NoteDetailsState
import com.caster.notes.dsl.features.details.domain.NoteSaved
import com.caster.notes.dsl.features.list.domain.HideLoading
import com.caster.notes.dsl.features.list.domain.ShowLoading
import com.caster.notes.dsl.model.Note
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface NoteDetailsScreen : LifecycleObserver {
    val event: LiveData<NoteDetailsEvent>

    fun bind(
        view: NoteDetailsView,
        observable: Observable<NoteDetailsState>
    ): Disposable
}

class NoteDetailsScreenImpl : NoteDetailsScreen {
    private val _event = MutableLiveData<NoteDetailsEvent>()
    override val event: LiveData<NoteDetailsEvent> = _event

    override fun bind(view: NoteDetailsView, observable: Observable<NoteDetailsState>): Disposable {
        return CompositeDisposable().apply {
            setupContent(view, observable, this)
            setupLoading(view, observable, this)
            setupError(view, observable, this)
        }
    }

    fun withNote(note: Note, view: NoteDetailsView) {
        view.titleET.setText(note.title)
        view.contentET.setText(note.content)
    }

    fun withSaveButtonClick(view: NoteDetailsView) {
        view.saveButton.setOnClickListener {
            val title = view.getTitle()
            val content = view.getContent()
            _event.value = SubmitClicked(title, content)
        }
    }

    fun withInputActionDone(view: NoteDetailsView) {
        view.contentET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.saveButton.callOnClick()
                true
            }
            false
        }
    }

    private fun setupLoading(
        view: NoteDetailsView,
        observable: Observable<NoteDetailsState>,
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
        view: NoteDetailsView,
        observable: Observable<NoteDetailsState>,
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
        view: NoteDetailsView,
        observable: Observable<NoteDetailsState>,
        compositeDisposable: CompositeDisposable
    ) {

    }
}