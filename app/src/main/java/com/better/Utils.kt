package com.better

import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.US

object Utils {

    /**
     * convert date to string format
     *
     * @param date date
     * @return date string
     */
    @JvmStatic
    fun toSimpleString(date: Date): String {
        val dateFormat = "yyyy-MM-dd"
        val format = SimpleDateFormat(dateFormat, US)
        return format.format(date)
    }
}
