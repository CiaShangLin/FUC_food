package com.shang.fcu_food

import android.support.v7.widget.Toolbar
import com.shang.fcu_food.Main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadow.api.Shadow

@RunWith(RobolectricTestRunner::class)
class RoboTest {

    @Test
    fun MainActivityTest(){
        var mainActivity=Robolectric.setupActivity(MainActivity::class.java);
        var toolbar=mainActivity.findViewById<Toolbar>(R.id.toolbar)
        var shadow=Shadows.shadowOf(toolbar) as Toolbar


        shadow.performClick()



    }
}