package com.github.chojmi.bodyfattracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity.*

//http://www.free-online-calculator-use.com/body-composition-calculator.html#Tricep
//https://fitties.com/fat-caliper-plus/body-fat-calculation-methods/jackson-pollock-3/
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ -> startActivity(Intent(this, AddMeasurementsJP3Activity::class.java)) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
