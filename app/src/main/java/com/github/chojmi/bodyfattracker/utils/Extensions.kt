package com.github.chojmi.bodyfattracker.utils

import android.widget.EditText

fun EditText.getTextAsFloat() : Float {
    return text.toString().toFloat()
}