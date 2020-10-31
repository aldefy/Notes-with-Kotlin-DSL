package com.caster.notes.dsl.features.details.presentation

sealed class NoteDetailsEvent
object AddNoteSuccess : NoteDetailsEvent()
data class SubmitClicked(val title: String, val content: String) : NoteDetailsEvent()
