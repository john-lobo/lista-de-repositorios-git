package com.jlndev.listaderepositriosgit.utils.ext

import android.widget.ProgressBar

fun ProgressBar.showLoading(isLoading: Boolean) {
    if (isLoading) {
        visible(true)
    } else {
        gone(true)
    }
}



