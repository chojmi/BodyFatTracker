package com.github.chojmi.bodyfattracker

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater

class AddResultView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.add_result_view, this)
    }
}