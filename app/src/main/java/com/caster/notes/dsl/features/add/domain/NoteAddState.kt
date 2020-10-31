package com.caster.notes.dsl.features.add.domain

import com.caster.notes.dsl.common.ViewState
import com.caster.notes.dsl.features.list.domain.NotesState
import com.caster.notes.dsl.model.Note

sealed class NoteAddState : ViewState
object ShowLoading : NoteAddState()
object HideLoading : NoteAddState()

object NoteSaved : NoteAddState()
