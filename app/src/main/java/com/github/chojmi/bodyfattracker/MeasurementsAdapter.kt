package com.github.chojmi.bodyfattracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.measurements_view.view.*

class MeasurementsAdapter(private val results: List<MeasurementsResult>) : RecyclerView.Adapter<MeasurementsAdapter.ViewHolder>() {
    val deleteClicks: Observable<MeasurementsResult>
        get() = deleteClicksSubject

    private val deleteClicksSubject = PublishSubject.create<MeasurementsResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.measurements_view, parent, false))
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(measurementsResult: MeasurementsResult) {
            itemView.measurements_text.text = String.format("%s %s", measurementsResult.size, measurementsResult.measurementsUnit.unit)
            RxView.clicks(itemView.measurements_delete_btn).map { measurementsResult }.subscribe(deleteClicksSubject)
        }
    }
}