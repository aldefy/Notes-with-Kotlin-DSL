package com.caster.notes.dsl.features.list.di

import android.view.View
import com.caster.notes.dsl.common.di.component.CommonComponent
import com.caster.notes.dsl.features.add.presentation.NoteAddActivity
import com.caster.notes.dsl.features.list.presentation.NotesListActivity
import dagger.BindsInstance
import dagger.Component

@NotesScope
@Component(
    modules = [
        NotesModule::class
    ],
    dependencies = [CommonComponent::class]
)
interface NotesComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: View): Builder

        fun appComponent(commonComponent: CommonComponent): Builder

        fun build(): NotesComponent
    }

    fun inject(activity: NotesListActivity)
}