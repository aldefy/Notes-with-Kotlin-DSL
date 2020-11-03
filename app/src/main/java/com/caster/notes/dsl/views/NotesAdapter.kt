package com.caster.notes.dsl.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.bold
import com.caster.notes.dsl.common.color
import com.caster.notes.dsl.common.italic
import com.caster.notes.dsl.common.spannable
import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.findWithQuery
import com.perfomer.blitz.setTimeAgo
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(), Filterable {

    private var data: MutableList<Note> = mutableListOf()
    private var searchResults: MutableList<Note> = mutableListOf()
    private var onShowEmpty: () -> Unit = {}
    private var onHideEmpty: () -> Unit = {}
    private var onItemClick: (Note) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount() = searchResults.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(note = searchResults[position], onItemClick = onItemClick)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString().trim()
                val filterResults = FilterResults()
                filterResults.values = if (queryString.isEmpty())
                    data
                else {
                    data.findWithQuery(queryString)
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                searchResults.clear()
                searchResults.addAll(results.values as MutableList<Note>)
                notifyDataSetChanged()
                checkForEmptyResults()
            }
        }
    }

    fun setData(data: List<Note>) {
        this.data.clear()
        this.searchResults.clear()
        this.data.addAll(data)
        this.searchResults.addAll(data)
        notifyDataSetChanged()
        checkForEmptyResults()
    }

    fun withNoteClickListener(block: (Note) -> Unit) {
        onItemClick = block
    }

    fun withShowEmptyListener(block: () -> Unit) {
        onShowEmpty = block
    }

    fun withHideEmptyListener(block: () -> Unit) {
        onHideEmpty = block
    }

    private fun checkForEmptyResults() {
        if (searchResults.isEmpty()) {
            onShowEmpty()
        } else {
            onHideEmpty()
        }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            note: Note,
            onItemClick: (Note) -> Unit
        ) = with(note) {
            val titleSpannable = spannable {
                bold(color(color = R.color.primaryTextColor, s = title))
            }
            val contentSpannable = spannable {
                italic(color(R.color.secondaryTextColor, s = content))
            }
            itemView.tvNoteTitle.text = titleSpannable
            itemView.tvNoteText.text = contentSpannable
            itemView.tvElapsed.setTimeAgo(
                date = Date(updatedAt),
                showSeconds = false,
                autoUpdate = true
            )
            itemView.setOnClickListener {
                onItemClick(note)
            }
        }
    }
}