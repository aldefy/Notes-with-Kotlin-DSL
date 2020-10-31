package com.caster.notes.dsl.common.test

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor

object InstantTaskExecutorRule {
    @SuppressLint("RestrictedApi")
    fun start() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread() = true

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    fun tearDown() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}