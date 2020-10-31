package com.caster.notes.dsl.features.list.domain

import com.caster.notes.dsl.common.ViewState
import com.caster.notes.dsl.model.Note

sealed class NotesState : ViewState
object ShowLoading : NotesState()
object HideLoading : NotesState()

data class NotesFetched(val notes: List<Note>) : NotesState()
object NotesEmpty : NotesState()

data class ShowError(val throwable: Throwable) : NotesState()
object ShowEmpty: NotesState()