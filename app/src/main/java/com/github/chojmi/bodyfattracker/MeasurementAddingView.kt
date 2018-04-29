package com.github.chojmi.bodyfattracker

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import com.github.chojmi.bodyfattracker.model.MeasurementsUnit
import com.github.chojmi.bodyfattracker.utils.clearGlide
import com.github.chojmi.bodyfattracker.utils.getAverageText
import com.github.chojmi.bodyfattracker.utils.getTextAsDouble
import com.github.chojmi.bodyfattracker.utils.showUrl
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.measurement_adding_view.view.*

class MeasurementAddingView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    val nextBtnClicks: Observable<View>
        get() = RxView.clicks(measurement_adding_next_btn).map { measurement_adding_next_btn }
    val resultsObservable: Observable<List<MeasurementsResult>>
        get() = resultsSubject.doOnNext({ refreshAverage() })
    var screenState: ScreenState
        set(value) {
            results.clear()
            results.addAll(screenState.results)
            adapter.notifyDataSetChanged()
            refreshAverage()
            measurement_adding_edittext.append(value.typedResult)
        }
        get() = ScreenState(results, measurement_adding_edittext.text.toString())
    var measurementsSite: MeasurementsSite = MeasurementsSite.UNKNOWN
        set(value) {
            field = value
            measurement_adding_title.text = field.name
            measurement_adding_image.showUrl(field.jpgUrl)
        }
    var measurementsUnit: MeasurementsUnit = MeasurementsUnit.UNKNOWN
        set(value) {
            field = value
            measurement_adding_unit.text = field.unit
        }

    private lateinit var disposables: CompositeDisposable
    private val resultsSubject = PublishSubject.create<List<MeasurementsResult>>()
    private val results: MutableList<MeasurementsResult> = mutableListOf()

    private lateinit var adapter: MeasurementsAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        adapter = MeasurementsAdapter(results)

        adapter.deleteClicks
                .doOnNext({
                    results.remove(it)
                    adapter.notifyDataSetChanged()
                }).map { results }
                .subscribe(resultsSubject)

        RxView.clicks(measurement_adding_add_btn)
                .filter({ measurement_adding_edittext.text.isNotBlank() })
                .doOnNext({
                    results.add(0, MeasurementsResult(measurementsSite, measurement_adding_edittext.getTextAsDouble(), measurementsUnit))
                    adapter.notifyItemInserted(0)
                    measurement_adding_edittext.text.clear()
                }).map { results }
                .subscribe(resultsSubject)

        measurement_adding_list.adapter = adapter
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        disposables = CompositeDisposable()
        disposables.add(RxTextView.editorActionEvents(measurement_adding_edittext)
                .filter({ it.actionId() == EditorInfo.IME_ACTION_NEXT })
                .forEach({
                    measurement_adding_add_btn.callOnClick()
                }))
    }

    override fun onDetachedFromWindow() {
        disposables.clear()
        super.onDetachedFromWindow()
    }

    fun clearGlide() {
        measurement_adding_image.clearGlide()
    }

    private fun refreshAverage() {
        measurement_adding_summary.text = String.format("%s %s", results.getAverageText(), measurementsUnit.unit)
    }

    class ScreenState(val results: List<MeasurementsResult> = emptyList(), var typedResult: String = "")
}