package com.shang.fcu_food

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.flyco.tablayout.SlidingTabLayout
import com.shang.fcu_food.Main.MainActivity
import com.shang.fcu_food.Main.ViewPagerAdapter
import com.shang.fcu_food.Unit.PermissionUnit
import com.shang.fcu_food.Unit.PickPictureUnit
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog

@RunWith(RobolectricTestRunner::class)
class RoboTest {

    lateinit var mainActivity: MainActivity

    @Before
    fun setMainActivity() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
    }


    @Test
    fun MainActivity_Toolbar_Test() {
        var toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)

        Assert.assertEquals(mainActivity.resources.getString(R.string.menu_main_addShop)
            ,toolbar.menu.findItem(R.id.menu_main_addShop).title)
        Assert.assertEquals(mainActivity.resources.getString(R.string.app_name),toolbar.title)
        Assert.assertEquals(toolbar.menu.size(),1)
        Assert.assertTrue(toolbar.menu.findItem(R.id.menu_main_addShop).isVisible)
        Assert.assertTrue(toolbar.menu.findItem(R.id.menu_main_addShop).isEnabled)

    }

    @Test
    fun MainActivity_Nav_Test(){
        var nav_view=mainActivity.findViewById<NavigationView>(R.id.nav_view)
        Assert.assertEquals(nav_view.menu.size(),3)
        Assert.assertEquals(mainActivity.getString(R.string.Menu_Navigation_User_Setting)
            ,nav_view.menu.findItem(R.id.menu_user_setting).title)
        Assert.assertEquals(mainActivity.getString(R.string.Menu_Navigation_User_Question)
            ,nav_view.menu.findItem(R.id.menu_question).title)
        Assert.assertEquals(mainActivity.getString(R.string.Menu_Navigation_User_Logout)
            ,nav_view.menu.findItem(R.id.menu_logout).title)

        nav_view.menu.performIdentifierAction(R.id.menu_question,0)
        var userQuestionDialog=ShadowAlertDialog.getLatestAlertDialog()
        Assert.assertNotNull(userQuestionDialog)
        Assert.assertTrue(userQuestionDialog.isShowing)

        userQuestionDialog.dismiss()
        Assert.assertFalse(userQuestionDialog.isShowing)

    }

    @Test
    fun MainActivity_slidingTab_test(){
        var viewPager=mainActivity.findViewById<ViewPager>(R.id.viewPager)
        var slidingTab=mainActivity.findViewById<SlidingTabLayout>(R.id.slidingTab)

        var adapter=ViewPagerAdapter(mainActivity.supportFragmentManager
            ,mainActivity.resources.getStringArray(R.array.ShopType))
        viewPager.adapter=adapter
        slidingTab.setViewPager(viewPager)

        Assert.assertEquals(4,adapter.count)
        Assert.assertEquals(4,slidingTab.tabCount)
        Assert.assertEquals("早餐",adapter.title[0])
        Assert.assertEquals("晚餐",adapter.title[1])
        Assert.assertEquals("小吃",adapter.title[2])
        Assert.assertEquals("飲料",adapter.title[3])
    }

    @Test
    fun PermissionUnit_Test(){
        Assert.assertTrue(PermissionUnit.checkPermission(mainActivity))
    }

    @Test
    fun PickPictureUnit_Test(){
        var bitmap= Bitmap.createBitmap(1080,720,Bitmap.Config.ARGB_8888)
        Assert.assertNotNull(PickPictureUnit.bitmapToByte(bitmap))
    }




}