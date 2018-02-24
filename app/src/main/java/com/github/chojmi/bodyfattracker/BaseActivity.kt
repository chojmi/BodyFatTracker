package com.github.chojmi.bodyfattracker

import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import com.mikepenz.iconics.context.IconicsLayoutInflater2

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
    }
}