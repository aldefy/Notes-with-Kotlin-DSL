package com.caster.notes.dsl.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
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
        linearLayoutManager.reverseLayout = false
        notesRecyclerView.layoutManager = linearLayoutManager
        notesRecyclerView.adapter = adapter
        notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    fun setData(data: List<Note>) {
        adapter.setData(data)
    }

    fun setNoteClickListener(clickListener: NotesAdapter.NoteClickListener) {
        adapter.setNoteClickListener(clickListener)
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
}