package com.caster.notes.dsl.common.showcase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import com.caster.notes.dsl.model.Note
import com.perfomer.blitz.setTimeAgo
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

class NotesRVAdapter :
    RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder>(), Filterable {

    private var data: MutableList<NoteModel> = mutableListOf()
    private var searchResults: MutableList<NoteModel> = mutableListOf()
    private var clickListener: NoteClickListener? = null
    private var search: String= ""

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
                search = queryString
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
                searchResults.addAll(results.values as MutableList<NoteModel>)
                notifyDataSetChanged()
            }
        }
    }

    fun setData(data: List<NoteModel>) {
        this.data.clear()
        this.searchResults.clear()
        this.data.addAll(data)
        this.searchResults.addAll(data)
        filter.filter(search)
    }

    fun addNote(data: NoteModel) {
        this.data.add(data)
        this.searchResults.add(data)
        filter.filter(search)
    }

    fun addNotes(data: List<NoteModel>) {
        this.data.addAll(data)
        this.searchResults.addAll(data)
        filter.filter(search)
    }

    fun setNoteClickListener(clickListener: NoteClickListener?) {
        this.clickListener = clickListener
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            note: NoteModel,
            clickListener: NoteClickListener?
        ) = with(note) {
            itemView.tvNoteTitle.text = title
            itemView.tvNoteText.text = content
            itemView.tvElapsed.setTimeAgo(
                date = Date(updatedAt),
                showSeconds = false,
                autoUpdate = true
            )
            itemView.setOnClickListener {
                clickListener?.noteClicked(note = note)
            }
        }
    }

    interface NoteClickListener {
        fun noteClicked(note: NoteModel)
    }
}