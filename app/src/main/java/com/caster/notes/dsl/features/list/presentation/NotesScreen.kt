package com.caster.notes.dsl.features.list.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.bind
import com.caster.notes.dsl.features.list.domain.HideLoading
import com.caster.notes.dsl.features.list.domain.NotesFetched
import com.caster.notes.dsl.features.list.domain.NotesState
import com.caster.notes.dsl.features.list.domain.ShowLoading
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

    fun withFAB(view: NotesView) {
        view.fab.setOnClickListener {
            _event.value = FABClickedEvent
        }
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
    }

    private fun setupError(
        view: NotesView,
        observable: Observable<NotesState>,
        compositeDisposable: CompositeDisposable
    ) {

    }
}