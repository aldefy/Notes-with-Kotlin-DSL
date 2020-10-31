package com.caster.notes.dsl.features.list.di

import androidx.lifecycle.ViewModel
import com.caster.notes.dsl.common.ViewModelKey
import com.caster.notes.dsl.common.di.module.ViewModelModule
import com.caster.notes.dsl.features.add.domain.NoteAddViewModel
import com.caster.notes.dsl.features.list.domain.*
import com.caster.notes.dsl.features.list.presentation.NotesView
import com.caster.notes.dsl.features.list.presentation.NotesViewImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
abstract class NotesModule {

    @Binds
    abstract fun repository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun useCase(impl: NotesUseCaseImpl): NotesUseCase

    @Binds
    abstract fun view(impl: NotesViewImpl): NotesView

    @Binds
    @IntoMap
    @ViewModelKey(NotesListViewModel::class)
    abstract fun viewModel(vm: NotesListViewModel): ViewModel
}