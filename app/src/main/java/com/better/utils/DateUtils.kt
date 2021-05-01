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

    fun toPostCreatedString(date: Date): String {
        val dateFormat = "EEE HH:mm"
        val format = SimpleDateFormat(dateFormat, Locale.US)
        return format.format(date)
    }

    fun getCalendarFromTimestamp(timestamp: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp * 1000

        return cal
    }

    fun getWeekDayFromCalendar(calendar: Calendar): String {
        val formattedDate =
            DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH).format(calendar.time)
        val splitDate = formattedDate.split(',')
        return splitDate[0]
    }

    fun getMonthAndYearFromCalendar(calendar: Calendar): String {
        val formattedDate =
            DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH).format(calendar.time)
        val splitDate = formattedDate.split(',')
        val year = splitDate[2]
        val month = splitDate[1].split(' ')[1]
        return "$month $year"
    }

    fun getWeekDayAndDateFromCalendar(calendar: Calendar): String {
        val splitDate = calendar.time.toString().split(' ')
        return "${splitDate[0]} ${splitDate[2]}"
    }

    fun getHourFromCalendar(calendar: Calendar): String {
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return String.format("%02d:%02d", hours, minutes)
    }
}
