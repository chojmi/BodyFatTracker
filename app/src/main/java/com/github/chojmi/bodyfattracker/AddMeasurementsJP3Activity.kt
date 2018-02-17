package com.github.chojmi.bodyfattracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.chojmi.bodyfattracker.model.MeasurementsSite
import kotlinx.android.synthetic.main.add_measurements_jp3_activity.*

class AddMeasurementsJP3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_measurements_jp3_activity)
        add_measurements_result_view.measurementsSite = MeasurementsSite.CHEST
    }
}
