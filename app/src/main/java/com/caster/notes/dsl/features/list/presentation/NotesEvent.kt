package com.caster.notes.dsl.features.list.presentation

import com.caster.notes.dsl.model.Note

sealed class NotesEvent
data class NotesFetchedEvent(val notes: List<Note>) : NotesEvent()
object FABClickedEvent: NotesEvent()
data class NoteClickedEvent(val note: Note) : NotesEvent()
data class ShowErrorEvent(val throwable: Throwable) : NotesEvent()