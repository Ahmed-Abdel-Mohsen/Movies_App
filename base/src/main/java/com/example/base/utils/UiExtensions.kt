package com.example.base.utils

import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast

fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeGone() {
    this?.visibility = View.GONE
}

fun View?.makeVisibleOrGone(shouldBeVisible: Boolean) {
    if (shouldBeVisible) makeVisible() else makeGone()
}

fun View?.makeInvisible() {
    this?.visibility = View.INVISIBLE
}

fun Activity.showToast(text: CharSequence?) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun TextView.setTextOrHideIfNull(text: String?) {
    if (text != null) {
        makeVisible()
        setText(text)
    } else {
        makeGone()
    }
}