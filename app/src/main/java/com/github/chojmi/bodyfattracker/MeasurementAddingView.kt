package com.github.chojmi.bodyfattracker

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import kotlinx.android.synthetic.main.measurement_adding_view.view.*

class MeasurementAddingView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    var measurementsSite: MeasurementsSite = MeasurementsSite.UNKNOWN
        set(value) {
            field = value
            measurement_adding_title.text = field.name
        }
    private val results: MutableList<MeasurementsResult> = mutableListOf()
    private lateinit var adapter: MeasurementsAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        adapter = MeasurementsAdapter(results) {
            results.remove(it)
            adapter.notifyDataSetChanged()
            refreshAverage()
        }
        measurement_adding_list.adapter = adapter
        measurement_adding_add_btn.setOnClickListener({
            if (measurement_adding_edittext.text.isEmpty()) {
                return@setOnClickListener
            }
            results.add(0, MeasurementsResult(measurementsSite, measurement_adding_edittext.text.toString().toInt()))
            adapter.notifyItemInserted(0)
            refreshAverage()
        })
    }

    private fun refreshAverage() {
        measurement_adding_summary.text = results.map { it.size }.average().toString()
    }
}