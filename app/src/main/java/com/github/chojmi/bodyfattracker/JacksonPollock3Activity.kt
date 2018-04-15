package com.github.chojmi.bodyfattracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import com.github.chojmi.bodyfattracker.model.MeasurementsResult
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_AGE
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_MEASUREMENT
import com.github.chojmi.bodyfattracker.utils.calculateJacksonPollock3
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.jackson_pollock_3_activity.*
import java.util.*

class JacksonPollock3Activity : BaseActivity() {
    private val adapter = JacksonPollock3Adapter()
    private var actualMeasurements: Map<MeasurementsSite, List<MeasurementsResult>> = Collections.emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jackson_pollock_3_activity)
        disposables.addAll(adapter.nextClicks.subscribe {
            if (it == jackon_pollock_3_result_view_pager.size) {
                checkIfCanFinish { finish() }
            } else {
                jackon_pollock_3_result_view_pager.currentItem = it + 1
            }
        }, adapter.results.subscribe { actualMeasurements = it })
        jackon_pollock_3_result_view_pager.adapter = adapter
    }

    private fun checkIfCanFinish(onFinish: () -> Unit) {
        disposables.add(Observable.fromIterable(adapter.screens)
                .filter({ !actualMeasurements.keys.contains(it) })
                .map { adapter.screens.indexOf(it) }
                .first(adapter.screens.size)
                .subscribe(Consumer {
                    if (it == adapter.screens.size) {
                        setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_MEASUREMENT, actualMeasurements.calculateJacksonPollock3(intent.getDoubleExtra(EXTRA_AGE, 0.0))) })
                        onFinish.invoke()
                    } else {
                        jackon_pollock_3_result_view_pager.currentItem = it
                        Toast.makeText(this, "There is lacking some data here...", Toast.LENGTH_SHORT).show()
                    }
                }))

    }

    fun onMeasurementAddingViewCloseClick(view: View) {
        finish()
    }
}
