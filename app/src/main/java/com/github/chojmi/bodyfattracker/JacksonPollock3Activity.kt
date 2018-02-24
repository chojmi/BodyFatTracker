package com.github.chojmi.bodyfattracker

import android.os.Bundle
import kotlinx.android.synthetic.main.jackson_pollock_3_activity.*

class JacksonPollock3Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jackson_pollock_3_activity)
        jackon_pollock_3_result_view_pager.adapter = JacksonPollock3Adapter {

        }
    }
}
