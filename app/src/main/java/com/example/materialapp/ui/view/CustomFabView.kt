package com.example.materialapp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import com.example.materialapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CustomFabView(context: Context, attributeSet: AttributeSet) :
    FloatingActionButton(context, attributeSet) {

    override fun onDraw(canvas: Canvas?) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            val typedValue = TypedValue()
            context.theme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
            color = typedValue.data
        }
        val path = Path()
        val text = context.resources.getString(R.string.fab_info_stroke)
        path.addCircle(width / 2f, height / 2f, width / 2f - 12f, Path.Direction.CW)
        canvas?.drawTextOnPath(text, path, 0f, 0f, paint)
        super.onDraw(canvas)
    }
}