package com.luka.grcki_kino_mozzart.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getFormattedDate(date: Date, dateFormat: String): String {
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun getXDaysInPastFromDate(date: Date, daysInPast: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_YEAR, -daysInPast)
    return calendar.time
}