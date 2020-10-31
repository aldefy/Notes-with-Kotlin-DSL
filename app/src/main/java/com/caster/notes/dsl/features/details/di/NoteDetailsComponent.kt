package com.caster.notes.dsl.features.details.di

import android.view.View
import com.caster.notes.dsl.common.di.component.CommonComponent
import com.caster.notes.dsl.features.details.presentation.NoteAddActivity
import dagger.BindsInstance
import dagger.Component

@NoteDetailsScope
@Component(
    modules = [
        NotesDetailsModule::class
    ],
    dependencies = [CommonComponent::class]
)
interface NotesAddComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: View): Builder

        fun appComponent(commonComponent: CommonComponent): Builder

        fun build(): NotesAddComponent
    }

    fun inject(activity: NoteAddActivity)
}