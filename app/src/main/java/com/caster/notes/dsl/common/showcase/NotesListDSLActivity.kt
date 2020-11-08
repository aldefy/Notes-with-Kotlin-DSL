package com.caster.notes.dsl.common.showcase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caster.notes.dsl.R
import kotlinx.android.synthetic.main.activity_notes.toolbar
import kotlinx.android.synthetic.main.activity_notes_dsl.*

class NotesListDSLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_dsl)
        setSupportActionBar(toolbar)
        val recyclerView = notesRecyclerView.dsl {
            withLayoutManager(
                LinearLayoutManager(
                    this@NotesListDSLActivity,
                    RecyclerView.VERTICAL,
                    false
                )
            )
            withField<NoteModel> {
                title = "note zero"
                content = "Showcasing this note from dsl construct"
            }
            withClick { model, position ->
                Toast.makeText(
                    this@NotesListDSLActivity,
                    "Note clicked :${model.getValue()}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        recyclerView.getAdapter().addModels(generateSampleNotes())
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