package com.github.chojmi.bodyfattracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_AGE
import com.github.chojmi.bodyfattracker.utils.Constans.Companion.EXTRA_MEASUREMENT
import com.github.chojmi.bodyfattracker.utils.getTextAsDouble
import kotlinx.android.synthetic.main.main_activity.*

//
//https://fitties.com/fat-caliper-plus/body-fat-calculation-methods/jackson-pollock-3/
class MainActivity : BaseActivity() {

    companion object {
        const val REQUEST_CODE_JACKON_POLLOCK_3 = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            if (main_age_edittext.text.isBlank()) {
                return@setOnClickListener
            }
            startActivityForResult(Intent(this,
                    JacksonPollock3Activity::class.java).apply { putExtra(EXTRA_AGE, main_age_edittext.getTextAsDouble()) },
                    REQUEST_CODE_JACKON_POLLOCK_3)
        }
        main_age_edittext.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                fab.callOnClick()
            }
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_JACKON_POLLOCK_3 -> {
                if (resultCode == Activity.RESULT_OK) {
                    main_result_text.text = getString(R.string.common_percent_value, data?.getDoubleExtra(EXTRA_MEASUREMENT, 0.0).toString())
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
