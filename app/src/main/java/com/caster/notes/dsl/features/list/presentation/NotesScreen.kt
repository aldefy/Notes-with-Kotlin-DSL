package com.caster.notes.dsl.features.list.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.bind
import com.caster.notes.dsl.features.list.domain.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface NotesScreen : LifecycleObserver {
    val event: LiveData<NotesEvent>

    fun bind(
        view: NotesView,
        observable: Observable<NotesState>
    ): Disposable

    fun searchNotes(query: String, view: NotesView)
}

class NotesScreenImpl : NotesScreen {
    private val _event = MutableLiveData<NotesEvent>()
    override val event: LiveData<NotesEvent> = _event

    override fun bind(view: NotesView, observable: Observable<NotesState>): Disposable {
        return CompositeDisposable().apply {
            setupContent(view, observable, this)
            setupLoading(view, observable, this)
            setupError(view, observable, this)
        }
    }

    override fun searchNotes(query: String, view: NotesView) {
        view.notesContentView.searchNotes(query)
    }

    fun withNoteClickHandler(view: NotesView) {
        view.notesContentView.setNoteClickListener { note ->
            _event.value = NoteClickedEvent(note)
        }
    }

    fun withFAB(view: NotesView) {
        view.fab.setOnClickListener {
            _event.value = FABClickedEvent
        }
    }

    fun showError(view: NotesView, throwable: Throwable) {
        view.notesContentView.showError(
            message = throwable.message ?: "Error"
        )
    }

    private fun setupLoading(
        view: NotesView,
        observable: Observable<NotesState>,
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
        view: NotesView,
        observable: Observable<NotesState>,
        compositeDisposable: CompositeDisposable
    ) {
        observable
            .ofType(NotesFetched::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                view.notesContentView.setData(it.notes)
                _event.value = NotesFetchedEvent(it.notes)
                Unit
            }
            .subscribe()
            .addTo(compositeDisposable)

        observable
            .ofType(NotesEmpty::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .map { Unit }
            .bind(view.showEmpty())
            .addTo(compositeDisposable)
    }

    private fun setupError(
        view: NotesView,
        observable: Observable<NotesState>,
        compositeDisposable: CompositeDisposable
    ) {
        observable
            .ofType(ShowError::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                _event.value = ShowErrorEvent(it.throwable)
                Unit
            }
            .subscribe()
            .addTo(compositeDisposable)

        observable
            .ofType(ShowEmpty::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Unit
            }
            .bind(view.showEmpty())
            .addTo(compositeDisposable)
    }
}