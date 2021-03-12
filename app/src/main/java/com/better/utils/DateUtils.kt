package com.better.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * convert date to string format
     *
     * @param date date
     * @return date string
     */
    @JvmStatic
    fun toSimpleString(date: Date): String {
        val dateFormat = "yyyy-MM-dd"
        val format = SimpleDateFormat(dateFormat, Locale.US)
        return format.format(date)
    }

    fun getCalendarFromTimestamp(timestamp: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp * 1000

        return cal
    }

    fun getWeekDayFromCalendar(calendar: Calendar): String {
        val formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        val splitDate = formattedDate.split(',')
        return splitDate[0]
    }

    fun getHourFromCalendar(calendar: Calendar): String {
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return String.format("%02d:%02d", hours, minutes);
    }
}
