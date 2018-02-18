package com.github.chojmi.bodyfattracker

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.github.chojmi.bodyfattracker.model.MeasurementResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import kotlinx.android.synthetic.main.add_result_view.view.*

class AddResultView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    var measurementsSite: MeasurementsSite = MeasurementsSite.UNKNOWN
        set(value) {
            field = value
            add_result_title.text = field.name
        }
    private val results: MutableList<MeasurementResult> = mutableListOf()
    private lateinit var adapter: ResultsAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        adapter = ResultsAdapter(results) {
            results.remove(it)
            adapter.notifyDataSetChanged()
            refreshAverage()
        }
        add_result_list.adapter = adapter
        add_result_btn.setOnClickListener({
            if (add_result_edittext.text.isEmpty()) {
                return@setOnClickListener
            }
            results.add(0, MeasurementResult(measurementsSite, add_result_edittext.text.toString().toInt()))
            adapter.notifyItemInserted(0)
            refreshAverage()
        })
    }

    private fun refreshAverage() {
        add_result_summary.text = results.map { it.size }.average().toString()
    }
}