package com.gb.opaltest.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.apply

const val DATE_FORMAT_LOCAL = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ssXXX"

fun Date.dateToString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return dateFormatter.format(this)
}

fun String.stringToDate(format: String): Date {
    val dateFormatter = SimpleDateFormat(format, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return dateFormatter.parse(this) ?: Date()
}