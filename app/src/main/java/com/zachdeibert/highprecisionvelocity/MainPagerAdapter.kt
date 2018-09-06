package com.zachdeibert.highprecisionvelocity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragments: Array<Fragment> = arrayOf(VelocityFragment.newInstance(), VelocityFragment.newInstance())

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
