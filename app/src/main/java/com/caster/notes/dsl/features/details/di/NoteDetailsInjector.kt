package com.caster.notes.dsl.features.details.di

import android.view.View
import com.caster.notes.dsl.common.di.CommonInjectHelper
import com.caster.notes.dsl.features.details.presentation.NoteAddActivity

object NoteDetailsInjector {
    fun of(
        activity: NoteAddActivity,
        view: View
    ) {
        DaggerNotesAddComponent.builder()
            .appComponent(
                CommonInjectHelper.provideCommonComponent(activity.applicationContext)
            )
            .view(view)
            .build()
            .inject(activity)
    }
}