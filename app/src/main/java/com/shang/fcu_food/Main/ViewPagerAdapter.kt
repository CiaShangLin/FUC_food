package com.shang.fcu_food.Main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.shang.fcu_food.Fragment.BreakfastFragment
import com.shang.fcu_food.Fragment.DinnerFragment
import com.shang.fcu_food.Fragment.DrinkFragment
import com.shang.fcu_food.Fragment.SnackFragment

class ViewPagerAdapter(fm: FragmentManager, var title: Array<String>) : FragmentPagerAdapter(fm) {

    var fragmentList: MutableList<Fragment> = mutableListOf()

    init {
        fragmentList.add(BreakfastFragment.getInstance())
        fragmentList.add(DinnerFragment.getInstance())
        fragmentList.add(SnackFragment.getInstance())
        fragmentList.add(DrinkFragment.getInstance())
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList.get(p0)
    }

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = title[position]

}