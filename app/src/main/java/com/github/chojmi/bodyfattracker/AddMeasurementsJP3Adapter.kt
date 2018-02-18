package com.github.chojmi.bodyfattracker

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chojmi.bodyfattracker.model.MeasurementResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite

class AddMeasurementsJP3Adapter(val onChangeResultListener: (Map<MeasurementsSite, MeasurementResult>) -> Unit) : PagerAdapter() {
    private val screens : List<MeasurementsSite> = listOf(MeasurementsSite.CHEST, MeasurementsSite.THIGH, MeasurementsSite.ABDOMEN)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.add_result_view, container, false) as AddResultView
        layout.measurementsSite = screens[position]
        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = screens.size
}