package com.caster.notes.dsl.common.di

import android.content.Context
import com.caster.notes.dsl.common.CommonComponentProvider
import com.caster.notes.dsl.common.di.component.CommonComponent

object CommonInjectHelper {
    fun provideCommonComponent(applicationContext: Context): CommonComponent {
        return if (applicationContext is CommonComponentProvider) {
            (applicationContext as CommonComponentProvider).provideCommonComponent()
        } else {
            throw IllegalStateException("The application context you have passed does not implement CommonComponentProvider")
        }
    }
}