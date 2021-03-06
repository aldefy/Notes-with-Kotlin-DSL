package com.caster.notes.dsl.features.details.presentation

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import com.caster.notes.dsl.common.ProgressDialog
import com.caster.notes.dsl.common.RxUi
import com.caster.notes.dsl.common.multilineIme
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_details_note.view.*
import javax.inject.Inject

interface NoteDetailsView {
    val titleET: TextInputEditText
    val contentET: TextInputEditText
    val saveButton: Button
    val progressDialog: ProgressDialog

    fun getTitle(): String
    fun getContent(): String
    fun showLoading(): Function<Observable<Unit>, Disposable>
    fun hideLoading(): Function<Observable<Unit>, Disposable>
}

class NoteDetailsViewImpl @Inject constructor(view: View) : NoteDetailsView {
    override val saveButton: Button = view.btnSave
    override val contentET: TextInputEditText = view.etNoteContent.apply {
        multilineIme(EditorInfo.IME_ACTION_DONE)
    }
    override val titleET: TextInputEditText = view.etNoteTitle
    override val progressDialog: ProgressDialog = ProgressDialog(view.context)

    override fun getTitle(): String {
        return titleET.text.toString()
    }

    override fun getContent(): String {
        return contentET.text.toString()
    }

    override fun showLoading(): Function<Observable<Unit>, Disposable> = RxUi.ui {
        progressDialog.show()
    }

    override fun hideLoading(): Function<Observable<Unit>, Disposable> = RxUi.ui {
        progressDialog.hide()
    }
}