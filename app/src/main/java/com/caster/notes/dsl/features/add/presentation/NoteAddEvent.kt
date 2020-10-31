package com.caster.notes.dsl.features.add.presentation

sealed class NoteAddEvent
object AddNoteSuccess : NoteAddEvent()
data class SubmitClicked(val title: String, val content: String) : NoteAddEvent()
