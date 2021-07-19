package com.chul.booksearch.presentation.util

import android.util.Log

object LogHelper {
    private const val tag = "#Chul"
    fun log(msg: String) {
        Log.i(tag, msg)
    }
}