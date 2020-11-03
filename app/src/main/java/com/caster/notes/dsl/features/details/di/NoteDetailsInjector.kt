package com.caster.notes.dsl.features.details.di

import android.view.View
import com.caster.notes.dsl.common.di.CommonInjectHelper
import com.caster.notes.dsl.features.details.presentation.NoteDetailsActivity

object NoteDetailsInjector {
    fun of(
        activity: NoteDetailsActivity,
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