package com.caster.notes.dsl.features.list.di

import android.view.View
import com.caster.notes.dsl.common.di.CommonInjectHelper
import com.caster.notes.dsl.features.add.presentation.NoteAddActivity
import com.caster.notes.dsl.features.list.presentation.NotesListActivity

object NotesInjector {
    fun of(
        activity: NotesListActivity,
        view: View
    ) {
        DaggerNotesComponent.builder()
            .appComponent(
                CommonInjectHelper.provideCommonComponent(activity.applicationContext)
            )
            .view(view)
            .build()
            .inject(activity)
    }
}