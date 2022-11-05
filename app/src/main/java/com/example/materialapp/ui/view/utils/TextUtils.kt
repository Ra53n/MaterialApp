package com.example.materialapp.ui.view.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue

fun getUnderlinedBoldText(text: String) =
    SpannableString(text).apply {
        setSpan(
            UnderlineSpan(),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

fun getColoredFirstSentence(context: Context, text: String): Spannable {
    val explanationSpan = SpannableString(text)
    val firstSentenceIndex = text
        .split(".")
        .first()
        .length
    val typedValue = TypedValue()
    context.theme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
    explanationSpan.setSpan(
        ForegroundColorSpan(typedValue.data),
        0,
        firstSentenceIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    return explanationSpan
}

fun getBoldText(text: String) = SpannableString(text).apply {
    setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        length,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
    )
}