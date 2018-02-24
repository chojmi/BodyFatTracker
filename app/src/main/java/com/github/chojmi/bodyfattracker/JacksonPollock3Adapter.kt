package com.github.chojmi.bodyfattracker

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import com.github.chojmi.bodyfattracker.model.MeasurementsUnit

class JacksonPollock3Adapter(private val onChangeResultListener: (Map<MeasurementsSite, MeasurementsResult>) -> Unit) : PagerAdapter() {
    private val screens : List<MeasurementsSite> = listOf(MeasurementsSite.CHEST, MeasurementsSite.THIGH, MeasurementsSite.ABDOMEN)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.measurement_adding_view, container, false) as MeasurementAddingView
        layout.measurementsSite = screens[position]
        layout.measurementsUnit = MeasurementsUnit.METRICAL
        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = screens.size
}