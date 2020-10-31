package com.caster.notes.dsl.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import com.caster.notes.dsl.model.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var data: List<Note> = ArrayList()
    private var clickListener: NoteClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bind(note = data[position], clickListener = clickListener)

    fun setData(data: List<Note>) {
        this.data = data
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