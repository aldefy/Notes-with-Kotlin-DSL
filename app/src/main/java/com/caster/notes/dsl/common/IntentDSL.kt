package com.caster.notes.dsl.common

import android.content.Context
import android.content.Intent
import com.caster.notes.dsl.model.Note

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)
