package com.caster.notes.dsl.features.add.di

import androidx.lifecycle.ViewModel
import com.caster.notes.dsl.common.ViewModelKey
import com.caster.notes.dsl.common.di.module.ViewModelModule
import com.caster.notes.dsl.features.add.domain.*
import com.caster.notes.dsl.features.add.presentation.NoteAddView
import com.caster.notes.dsl.features.add.presentation.NoteAddViewImpl
import com.caster.notes.dsl.features.list.domain.NoteRepository
import com.caster.notes.dsl.features.list.domain.NoteRepositoryImpl
import com.caster.notes.dsl.features.list.domain.NotesUseCase
import com.caster.notes.dsl.features.list.domain.NotesUseCaseImpl
import com.caster.notes.dsl.features.list.presentation.NotesView
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
abstract class NoteAddModule {

    @Binds
    abstract fun repository(impl: NoteAddRepositoryImpl): NoteAddRepository

    @Binds
    abstract fun useCase(impl: NoteAddUseCaseImpl): NoteAddUseCase

    @Binds
    abstract fun view(impl: NoteAddViewImpl): NoteAddView

    @Binds
    @IntoMap
    @ViewModelKey(NoteAddViewModel::class)
    abstract fun viewModel(vm: NoteAddViewModel): ViewModel
}