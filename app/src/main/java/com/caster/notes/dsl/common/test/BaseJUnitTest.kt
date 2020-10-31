package com.caster.notes.dsl.common.test

import org.junit.After
import org.junit.Before

abstract class BaseJUnitTest {
    private val trampoline = TrampolineSchedulerRule()

    abstract fun start()
    abstract fun stop()

    @Before
    fun setup() {
        trampoline.start()
        InstantTaskExecutorRule.start()
        start()
    }

    @After
    fun tearDown() {
        stop()
        InstantTaskExecutorRule.tearDown()
        trampoline.tearDown()
    }
}
