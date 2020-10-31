package com.caster.notes.dsl.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import com.caster.notes.dsl.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(), Filterable {

    private var data: MutableList<Note> = mutableListOf()
    private var searchResults: MutableList<Note> = mutableListOf()
    private var clickListener: NoteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount() = searchResults.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(note = searchResults[position], clickListener = clickListener)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString().trim()
                val filterResults = FilterResults()
                filterResults.values = if (queryString.isEmpty())
                    data
                else
                    data.filter { row ->
                        row.title.contains(queryString, ignoreCase = true) or
                                row.content.contains(
                                    queryString,
                                    ignoreCase = true
                                )
                    }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                searchResults.clear()
                searchResults.addAll(results.values as MutableList<Note>)
                notifyDataSetChanged()
            }
        }
    }

    fun setData(data: List<Note>) {
        this.data.clear()
        this.searchResults.clear()
        this.data.addAll(data)
        this.searchResults.addAll(data)
        notifyDataSetChanged()
    }

    fun setNoteClickListener(clickListener: NoteClickListener?) {
        this.clickListener = clickListener
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            note: Note,
            clickListener: NoteClickListener?
        ) = with(note) {
            itemView.tvNoteTitle.text = title
            itemView.tvNoteText.text = content
            itemView.tvElapsed.text = "1 min ago"
            itemView.setOnClickListener {
                clickListener?.noteClicked(note = note)
            }
        }
    }

    interface NoteClickListener {
        fun noteClicked(note: Note)
    }
}