package com.jlndev.listaderepositriosgit.utils.ext

import android.widget.ProgressBar

fun ProgressBar.showLoading(isLoading: Boolean) {
    if (isLoading) {
        this.visibility = android.view.View.VISIBLE
    } else {
        this.visibility = android.view.View.GONE
    }
}



