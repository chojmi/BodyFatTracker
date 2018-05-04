package com.github.chojmi.bodyfattracker

import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        disposables = CompositeDisposable()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}