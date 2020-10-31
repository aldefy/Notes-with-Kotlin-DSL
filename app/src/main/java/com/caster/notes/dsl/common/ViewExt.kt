package com.caster.notes.dsl.common

import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

fun View.show() = this.apply {
    visibility = View.VISIBLE
}

fun View.hide() = this.apply {
    visibility = View.GONE
}

fun View.invisbile() = this.apply {
    visibility = View.INVISIBLE
}

fun View.enable() = this.apply {
    isEnabled = true
}

fun View.disable() = this.apply {
    isEnabled = false
}

fun EditText.disabled() = this.apply {
    this.isFocusable = false
    this.isCursorVisible = false
    this.inputType = InputType.TYPE_NULL
    this.keyListener = null
}

fun TextInputEditText.multilineIme(action: Int) {
    inputType = EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
    setHorizontallyScrolling(false)
    maxLines = 20
    setLines(10)
}
