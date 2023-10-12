package com.jlndev.listaderepositriosgit.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jlndev.listaderepositriosgit.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}