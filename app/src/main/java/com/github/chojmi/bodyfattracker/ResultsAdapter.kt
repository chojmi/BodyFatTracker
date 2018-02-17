package com.github.chojmi.bodyfattracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chojmi.bodyfattracker.model.MeasurementResult
import kotlinx.android.synthetic.main.results_view.view.*

class ResultsAdapter(val results: List<MeasurementResult>, val onDeleteListener: (MeasurementResult) -> Unit) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.results_view, parent, false))
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(results[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(measurementResult: MeasurementResult) {
            itemView.results_text.text = String.format("%s %s", measurementResult.size, measurementResult.measurementUnit)
            itemView.results_delete_btn.setOnClickListener({ onDeleteListener(measurementResult) })
        }
    }
}