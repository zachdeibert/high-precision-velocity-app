package com.zachdeibert.highprecisionvelocity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ViewPager>(R.id.viewPager).adapter = MainPagerAdapter(supportFragmentManager)
    }
}
