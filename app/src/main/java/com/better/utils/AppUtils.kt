package com.better.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object AppUtils {

    fun bindImage(imgView: ImageView, imgUrl: String?) {
        Glide
            .with(imgView.context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(imgView)
    }
}
