package com.github.chojmi.bodyfattracker

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import com.github.chojmi.bodyfattracker.model.MeasurementsUnit
import com.github.chojmi.bodyfattracker.utils.getAverageText
import com.github.chojmi.bodyfattracker.utils.getTextAsDouble
import kotlinx.android.synthetic.main.measurement_adding_view.view.*

class MeasurementAddingView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    var onChangeResultListener: ((List<MeasurementsResult>) -> Unit)? = null
    var onNextClickListener: (() -> Unit)? = null
    var measurementsSite: MeasurementsSite = MeasurementsSite.UNKNOWN
        set(value) {
            field = value
            measurement_adding_title.text = field.name
        }
    var measurementsUnit: MeasurementsUnit = MeasurementsUnit.UNKNOWN
        set(value) {
            field = value
            measurement_adding_unit.text = field.unit
        }

    private val results: MutableList<MeasurementsResult> = mutableListOf()
    private lateinit var adapter: MeasurementsAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        adapter = MeasurementsAdapter(results) {
            results.remove(it)
            adapter.notifyDataSetChanged()
            refreshAverage()
            onChangeResultListener?.invoke(results)
        }
        measurement_adding_list.adapter = adapter
        measurement_adding_add_btn.setOnClickListener({
            if (measurement_adding_edittext.text.isBlank()) {
                return@setOnClickListener
            }
            results.add(0, MeasurementsResult(measurementsSite, measurement_adding_edittext.getTextAsDouble(), measurementsUnit))
            adapter.notifyItemInserted(0)
            refreshAverage()
            onChangeResultListener?.invoke(results)
            measurement_adding_edittext.text.clear()
        })
        measurement_adding_edittext.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                measurement_adding_add_btn.callOnClick()
            }
            false
        })
        measurement_adding_next_btn.setOnClickListener( {
            if (results.isNotEmpty()) {
                onNextClickListener?.invoke()
            }
        })
    }

    fun setResults(newResults : List<MeasurementsResult>) {
        results.clear()
        results.addAll(newResults)
        adapter.notifyDataSetChanged()
        refreshAverage()
    }

    private fun refreshAverage() {
        measurement_adding_summary.text =  String.format("%s %s", results.getAverageText(), measurementsUnit.unit)
    }
}