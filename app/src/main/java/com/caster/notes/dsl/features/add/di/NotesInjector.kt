package com.caster.notes.dsl.features.add.di

import android.view.View
import com.caster.notes.dsl.common.di.CommonInjectHelper
import com.caster.notes.dsl.features.add.presentation.NoteAddActivity

object NoteAddInjector {
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