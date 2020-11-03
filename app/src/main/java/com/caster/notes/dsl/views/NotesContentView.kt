package com.caster.notes.dsl.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.hide
import com.caster.notes.dsl.common.show
import com.caster.notes.dsl.model.Note
import kotlinx.android.synthetic.main.view_notes.view.*

class NotesContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private val adapter = NotesAdapter()

    init {
        View.inflate(context, R.layout.view_notes, this)
        with(adapter) {
            withShowEmptyListener {
                this@NotesContentView.showEmpty()
            }
            withHideEmptyListener {
                this@NotesContentView.resetError()
            }
        }
        linearLayoutManager.reverseLayout = false
        notesRecyclerView.layoutManager = linearLayoutManager
        notesRecyclerView.adapter = adapter
        notesRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL).also {
                it.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_insets)!!)
            }
        )
    }

    fun setData(data: List<Note>) {
        resetError()
        adapter.setData(data)
    }

    fun setNoteClickListener(block: (Note) -> Unit) {
        adapter.withNoteClickListener(block)
    }

    fun showLoading() {
        shimmerLoadingView.show()
    }

    fun hideLoading() {
        shimmerLoadingView.hide()
    }

    fun searchNotes(query: String) {
        adapter.filter.filter(query)
    }

    fun showError(title: String? = null, message: String) {
        notesRecyclerView.hide()
        errorView.showError(title, message)
    }

    fun showEmpty() {
        notesRecyclerView.hide()
        errorView.showEmpty()
    }

    private fun hideError() {
        errorView.hide()
    }

    private fun resetError() {
        hideError()
        notesRecyclerView.show()
    }
}