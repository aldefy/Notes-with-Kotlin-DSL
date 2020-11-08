package com.caster.notes.dsl.common.showcase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import kotlinx.android.synthetic.main.activity_notes.toolbar
import kotlinx.android.synthetic.main.activity_notes_dsl.*

class NotesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        setSupportActionBar(toolbar)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        val adapter = NotesRVAdapter()
        adapter.setNoteClickListener(object : NotesRVAdapter.NoteClickListener {
            override fun noteClicked(note: NoteModel) {
                Toast.makeText(
                    this@NotesListActivity,
                    "Note clicked :${note.content}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
        notesRecyclerView.layoutManager = layoutManager
        notesRecyclerView.adapter = adapter
        adapter.addNote(
            NoteModel(
                title = "note zero",
                content = "Showcasing this note from dsl construct"
            )
        )
        val data = generateSampleNotes()
        adapter.addNotes(data)
    }

    private fun generateSampleNotes(): MutableList<NoteModel> {
        val data = mutableListOf<NoteModel>()
        for (i in 1..25) {
            data.add(
                NoteModel(
                    content = "Note content $i",
                    title = "Note title $i",
                    createdAt = System.currentTimeMillis()
                )
            )
        }
        return data
    }

}