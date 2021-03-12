package com.better.utils

import android.widget.ImageView
import com.better.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

    fun bindImage(imgView: ImageView, imgUrl: String?) {
        Glide
            .with(imgView.context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .placeholder(R.drawable.ic_menu_soccer)
            .into(imgView)
    }

}
