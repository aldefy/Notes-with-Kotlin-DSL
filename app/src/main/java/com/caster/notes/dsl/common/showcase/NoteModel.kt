package com.caster.notes.dsl.common.showcase

import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.caster.notes.dsl.R
import com.perfomer.blitz.setTimeAgo
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "notes")
@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String ="",
    var content: String ="",
    var createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = createdAt
) : DataModel(R.layout.item_note), Parcelable {
    override fun search(query: String): Boolean {
        return title.contains(query, ignoreCase = true) or
                content.contains(
                    query,
                    ignoreCase = true
                )
    }

    override fun bind(
        itemView: View,
        adapter: DSLRVAdapter,
        position: Int,
        event: (model: DataModel) -> Unit
    ) {
        itemView.findViewById<TextView>(R.id.tvNoteTitle).text = title
        itemView.findViewById<TextView>(R.id.tvNoteText).text = content
        itemView.findViewById<TextView>(R.id.tvElapsed).setTimeAgo(
            date = Date(updatedAt),
            showSeconds = false,
            autoUpdate = true
        )
        itemView.setOnClickListener {
            event(this)
        }
    }

    override fun getValue(): String {
        return content
    }
}