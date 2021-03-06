package com.caster.notes.dsl.features.list.presentation

import android.view.View
import com.caster.notes.dsl.common.RxUi
import com.caster.notes.dsl.features.list.domain.NotesFetched
import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.views.NoteSearchView
import com.caster.notes.dsl.views.NotesContentView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_notes.view.*
import javax.inject.Inject

interface NotesView {
    val fab: FloatingActionButton
    val notesContentView: NotesContentView
    val searchView: NoteSearchView
    fun hideLoading(): Function<Observable<Unit>, Disposable>
    fun showLoading(): Function<Observable<Unit>, Disposable>
    fun bindNotes(notes: List<Note>): Function<Observable<NotesFetched>, Disposable>
    fun showError(throwable: Throwable): Function<Observable<Unit>, Disposable>
    fun showEmpty(): Function<Observable<Unit>, Disposable>
}

class NotesViewImpl @Inject constructor(val view: View) : NotesView {
    override val fab: FloatingActionButton = view.fab
    override val notesContentView: NotesContentView = view.notesContentView
    override val searchView: NoteSearchView = NoteSearchView(view.context)

    override fun hideLoading(): Function<Observable<Unit>, Disposable> = RxUi.ui {
        notesContentView.hideLoading()
    }

    override fun showLoading(): Function<Observable<Unit>, Disposable> = RxUi.ui {
        notesContentView.showLoading()
    }

    override fun bindNotes(notes: List<Note>): Function<Observable<NotesFetched>, Disposable> =
        RxUi.ui {
            notesContentView.setData(notes)
        }

    override fun showError(throwable: Throwable): Function<Observable<Unit>, Disposable> = RxUi.ui {
        notesContentView.showError(
            message = throwable.message ?: "Error"
        )
    }

    override fun showEmpty(): Function<Observable<Unit>, Disposable> = RxUi.ui {
        notesContentView.showEmpty()
    }
}