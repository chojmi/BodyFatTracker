package com.github.chojmi.bodyfattracker.utils

import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite

fun EditText.getTextAsDouble(): Double {
    return if (text.isNotBlank()) text.toString().toDouble() else  0.0
}

fun List<MeasurementsResult>.getAverage(): Double {
    return map { it.size }.average()
}

fun List<MeasurementsResult>.getAverageText(): String {
    return getAverage().toString()
}

fun Map<MeasurementsSite, List<MeasurementsResult>>.calculateJacksonPollock3(age: Double): Double {
    if (age == 0.0 || !keys.containsAll(listOf(MeasurementsSite.CHEST, MeasurementsSite.ABDOMEN, MeasurementsSite.THIGH))) {
        return 0.0
    }
    val sumOfSkinfolds = values.map { it.getAverage() }.sum()
    val bodyDensity = 1.10938 - (0.0008267 * sumOfSkinfolds) + (0.0000016 * sumOfSkinfolds * sumOfSkinfolds) - (0.0002574 * age)
    return (495 / bodyDensity) - 450
}

fun ImageView.showUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.clearGlide() {
    Glide.with(this).clear(this)
}

