package com.caster.notes.dsl.common.di.module

import androidx.lifecycle.ViewModelProvider
import com.caster.notes.dsl.common.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}