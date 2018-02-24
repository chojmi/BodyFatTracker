package com.github.chojmi.bodyfattracker

import android.os.Bundle
import kotlinx.android.synthetic.main.add_measurements_jp3_activity.*

class AddMeasurementsJP3Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_measurements_jp3_activity)
        add_measurements_result_view_pager.adapter = AddMeasurementsJP3Adapter {

        }
    }
}
