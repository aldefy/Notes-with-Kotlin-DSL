package com.caster.notes.dsl.features.details.domain

import com.caster.notes.dsl.common.ViewState

sealed class NoteDetailsState : ViewState
object ShowLoading : NoteDetailsState()
object HideLoading : NoteDetailsState()

object NoteSaved : NoteDetailsState()
