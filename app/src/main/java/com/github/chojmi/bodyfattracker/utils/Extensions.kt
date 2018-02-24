package com.github.chojmi.bodyfattracker.utils

import android.widget.EditText
import com.github.chojmi.bodyfattracker.model.MeasurementsResult

fun EditText.getTextAsFloat() : Float {
    return text.toString().toFloat()
}

fun MutableList<MeasurementsResult>.getAverage() : String {
    return map { it.size }.average().toString()
}