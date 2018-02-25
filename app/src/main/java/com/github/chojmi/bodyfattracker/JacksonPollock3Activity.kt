package com.github.chojmi.bodyfattracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.view.size
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_AGE
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_MEASUREMENT
import com.github.chojmi.bodyfattracker.utils.calculateJacksonPollock3
import kotlinx.android.synthetic.main.jackson_pollock_3_activity.*

class JacksonPollock3Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jackson_pollock_3_activity)
        jackon_pollock_3_result_view_pager.adapter = JacksonPollock3Adapter({
            if(it == jackon_pollock_3_result_view_pager.size) {
                finish()
            } else {
                jackon_pollock_3_result_view_pager.currentItem = it + 1
            }
        }) {
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_MEASUREMENT, it.calculateJacksonPollock3(intent.getDoubleExtra(EXTRA_AGE, 0.0))) })
        }
    }
}
