package com.caster.notes.dsl.common.di.component

import android.content.Context
import com.caster.notes.dsl.common.di.module.CoreDataModule
import com.caster.notes.dsl.common.di.module.ViewModelModule
import com.caster.notes.dsl.model.NotesDatabase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        CoreDataModule::class
    ]
)
@Singleton
interface CommonComponent{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context) : Builder
        fun build() : CommonComponent
    }
    fun db(): NotesDatabase
}