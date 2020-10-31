package com.caster.notes.dsl.common

import com.caster.notes.dsl.common.di.component.CommonComponent

interface CommonComponentProvider {
    fun provideCommonComponent(): CommonComponent
}