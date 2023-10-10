package com.jlndev.listaderepositriosgit.utils.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

fun View.visible(animate: Boolean = false) {
    if(animate) {
        val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
        fadeIn.duration = 150
        fadeIn.addUpdateListener { _ ->
            visibility = View.VISIBLE
        }
        fadeIn.start()
    } else {
        visibility = View.VISIBLE
    }
}

fun View.gone(animate: Boolean = false) {
    if(animate) {
        val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
        fadeOut.duration = 150
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
        fadeOut.start()
    } else {
        visibility = View.GONE
    }
}