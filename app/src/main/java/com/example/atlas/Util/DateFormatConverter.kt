package com.example.atlas.Util

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object DateFormatConverter {
    fun convertDateFormat(dateToConvert: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

        return formatter.format(parser.parse(dateToConvert)!!)
    }

    fun convertDateTimeZone(dateToConvert: String): String {
        val convertedOrderDateTime = ZonedDateTime.parse(dateToConvert)

        return convertedOrderDateTime.withZoneSameInstant(ZoneId.systemDefault()).toString()
    }
}