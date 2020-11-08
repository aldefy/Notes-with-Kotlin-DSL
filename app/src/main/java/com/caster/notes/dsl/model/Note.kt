package com.caster.notes.dsl.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.caster.notes.dsl.common.showcase.NoteModel
import kotlinx.android.parcel.Parcelize
import kotlin.properties.Delegates

@Entity(tableName = "note")
@Parcelize
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var content: String,
    var createdAt: Long,
    var updatedAt: Long = createdAt
) : Parcelable {
    private constructor(builder: Builder) : this(
        title = builder.title,
        content = builder.content,
        createdAt = System.currentTimeMillis()
    )

    companion object {
        inline fun create(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var title: String by Delegates.notNull()
        var content: String by Delegates.notNull()

        fun build() = Note(this)
    }

    fun getShareableContent(): String {
        return "$title\n$content"
    }
}

/**
 * Method to check if a List<Note> contains a particular String/Sub-String in the tile or content.
 * Returns `true` if the match is found else returns false
 * @param query String that will be compared with the items in the List.
 */
infix fun List<Note>.findWithQuery(query: String): List<Note> {
    return filter { row ->
        row.title.contains(query, ignoreCase = true) or
                row.content.contains(
                    query,
                    ignoreCase = true
                )
    }
}

fun NoteModel.toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
}