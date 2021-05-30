package com.example.triviaapp.utils

import android.text.Html

@Suppress("DEPRECATION")
class DecodeHtml {
    companion object {
        fun decode(str: String): String {
            return Html.fromHtml(str).toString()
        }
    }
}