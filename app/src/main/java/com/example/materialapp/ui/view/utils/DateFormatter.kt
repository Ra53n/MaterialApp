package com.example.materialapp.ui.view.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

class DateFormatter {
    fun getDateForRequest(isYesterday: Boolean): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val date = Calendar.getInstance()
        if (isYesterday) {
            date.add(Calendar.DAY_OF_YEAR, -1)
        }
        return dateFormat.format(date)
    }
}