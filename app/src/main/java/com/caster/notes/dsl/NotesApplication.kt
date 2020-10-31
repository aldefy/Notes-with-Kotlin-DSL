package com.caster.notes.dsl

import android.app.Activity
import android.app.Application
import com.caster.notes.dsl.common.CommonComponentProvider
import com.caster.notes.dsl.common.di.component.CommonComponent
import com.caster.notes.dsl.common.di.component.DaggerCommonComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NotesApplication : Application(), CommonComponentProvider, HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val commonComponent: CommonComponent by lazy {
        DaggerCommonComponent.builder()
            .context(this@NotesApplication)
            .build()
    }

    override fun provideCommonComponent(): CommonComponent {
        return commonComponent
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}