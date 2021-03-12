package com.better.utils

import android.widget.ImageView
import com.better.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object Utils {

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
