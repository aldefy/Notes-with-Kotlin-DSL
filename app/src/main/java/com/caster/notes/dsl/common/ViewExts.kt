package com.caster.notes.dsl.common

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.multilineIme(action: Int) {
    inputType = EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
    setHorizontallyScrolling(false)
    maxLines = Integer.MAX_VALUE
}