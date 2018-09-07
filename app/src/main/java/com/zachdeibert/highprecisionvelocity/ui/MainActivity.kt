package com.zachdeibert.highprecisionvelocity.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.zachdeibert.highprecisionvelocity.R

class MainActivity : AppCompatActivity() {
    private var pager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager = findViewById(R.id.viewPager)
        pager?.adapter = MainPagerAdapter(supportFragmentManager)
    }

    fun openSettings(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val pager: ViewPager? = this.pager
        if (requestCode == RESTART_PERMISSION_REQUEST_CODE
                && grantResults.any{ i -> i == PackageManager.PERMISSION_GRANTED }
                && pager != null) {
            val fragment: VelocityFragment = (pager.adapter as MainPagerAdapter).getItem(pager.currentItem) as VelocityFragment
            fragment.system?.start(this)
        }
    }

    companion object {
        const val RESTART_PERMISSION_REQUEST_CODE: Int = 1
    }
}
