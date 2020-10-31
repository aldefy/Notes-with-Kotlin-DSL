package com.caster.notes.dsl.features.details.di

import androidx.lifecycle.ViewModel
import com.caster.notes.dsl.common.ViewModelKey
import com.caster.notes.dsl.common.di.module.ViewModelModule
import com.caster.notes.dsl.features.details.domain.*
import com.caster.notes.dsl.features.details.presentation.NoteDetailsView
import com.caster.notes.dsl.features.details.presentation.NoteDetailsViewImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
abstract class NotesDetailsModule {

    @Binds
    abstract fun repository(impl: NoteDetailsRepositoryImpl): NoteDetailsRepository

    @Binds
    abstract fun useCase(impl: NoteDetailsUseCaseImpl): NoteDetailsUseCase

    @Binds
    abstract fun view(impl: NoteDetailsViewImpl): NoteDetailsView

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailsViewModel::class)
    abstract fun viewModel(vm: NoteDetailsViewModel): ViewModel
}