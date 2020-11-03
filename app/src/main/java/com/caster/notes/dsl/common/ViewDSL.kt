package com.caster.notes.dsl.common

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.TextUtils
import android.text.style.*

fun spannable(func: () -> SpannableString) = func()

private fun span(s: CharSequence, o: Any) =
    (if (s is String) SpannableString(s) else s as? SpannableString
        ?: SpannableString(""))
        .apply { setSpan(o, 0, length, SPAN_EXCLUSIVE_EXCLUSIVE) }

operator fun SpannableString.plus(s: SpannableString) =
    SpannableString(TextUtils.concat(this, s))

operator fun SpannableString.plus(s: String) =
    SpannableString(TextUtils.concat(this, s))

fun bold(s: CharSequence) =
    span(s, StyleSpan(Typeface.BOLD))
fun italic(s: CharSequence) =
    span(s, StyleSpan(Typeface.ITALIC))
fun sub(s: CharSequence) =
    span(s, SubscriptSpan()) // baseline is lowered
fun size(size: Float, s: CharSequence) =
    span(s, RelativeSizeSpan(size))
fun color(color: Int, s: CharSequence) =
    span(s, ForegroundColorSpan(color))
fun url(url: String, s: CharSequence) =
    span(s, URLSpan(url))