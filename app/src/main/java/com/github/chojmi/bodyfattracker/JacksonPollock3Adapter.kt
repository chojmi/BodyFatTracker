package com.github.chojmi.bodyfattracker

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import com.github.chojmi.bodyfattracker.model.MeasurementsUnit
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

class JacksonPollock3Adapter : PagerAdapter() {
    val results: Observable<Map<MeasurementsSite, List<MeasurementsResult>>>
        get() = resultsSubject
    val nextClicks: Observable<Int>
        get() = nextClicksSubject
    val screens: List<MeasurementsSite> = listOf(MeasurementsSite.CHEST, MeasurementsSite.THIGH, MeasurementsSite.ABDOMEN)

    private val screensState: MutableMap<Int, MeasurementAddingView.ScreenState> = mutableMapOf()
    private val nextClicksSubject = PublishSubject.create<Int>()
    private val resultsSubject = BehaviorSubject.createDefault<Map<MeasurementsSite, List<MeasurementsResult>>>(Collections.emptyMap())

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.measurement_adding_view, container, false) as MeasurementAddingView
        layout.measurementsSite = screens[position]
        layout.measurementsUnit = MeasurementsUnit.METRICAL

        layout.resultsObservable
                .doOnNext({ screensState[position] = MeasurementAddingView.ScreenState(it) })
                .map { screensState.mapKeys { screens[it.key] }.mapValues { it.value.results } }
                .subscribe(resultsSubject)

        layout.nextBtnClicks.map { _ -> position }.subscribe(nextClicksSubject)
        layout.screenState = screensState[position] ?: MeasurementAddingView.ScreenState()
        if(position == screens.size -1) layout.setClosingButtonText(R.string.measurement_adding_close)

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as MeasurementAddingView
        screensState[position] = view.screenState
        view.clearGlide()
        container.removeView(view)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = screens.size
}