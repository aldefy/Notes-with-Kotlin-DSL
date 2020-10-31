package com.caster.notes.dsl.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note")
@Parcelize
data class Note(
    /**
     * Note Title
     */
    var title: String,
    /**
     * Really long user note in string
     */
    var content: String,
    /**
     * mills in Long from DateTime - the date when note was created
     */
    var createdAt: Long,
    /**
     * mills in Long from DateTime - the date when note was updated
     */
    var updatedAt: Long = createdAt,
    /**
     * Auto generated id
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Parcelable