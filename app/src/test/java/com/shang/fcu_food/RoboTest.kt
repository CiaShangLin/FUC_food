package com.shang.fcu_food

import android.content.Intent
import android.graphics.Bitmap
import android.media.Rating
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputLayout
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.RatingBar
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.flyco.tablayout.SlidingTabLayout
import com.google.firebase.FirebaseApp
import com.shang.fcu_food.Data.shop.BreakfastShop
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.DetailShop.DetailShopActivity
import com.shang.fcu_food.DetailShop.DetailShopVH
import com.shang.fcu_food.Dialog.AddCommentDialog
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.Dialog.AddShopDialog
import com.shang.fcu_food.Main.MainActivity
import com.shang.fcu_food.Main.ViewPagerAdapter
import com.shang.fcu_food.Unit.*
import kotlinx.android.synthetic.main.dialog_addcomment.*
import kotlinx.android.synthetic.main.dialog_addmenu.*
import kotlinx.android.synthetic.main.dialog_addshop.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog


@RunWith(RobolectricTestRunner::class)
class RoboTest {

    lateinit var mainActivity: MainActivity

    @Before
    fun setMainActivity() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().visible().get()
        FirebaseApp.initializeApp(mainActivity)
    }

    @Test
    fun MainActivity_Toolbar_Test() {
        var toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)

        Assert.assertEquals(
            mainActivity.resources.getString(R.string.menu_main_addShop)
            , toolbar.menu.findItem(R.id.menu_main_addShop).title
        )
        Assert.assertEquals(mainActivity.resources.getString(R.string.app_name), toolbar.title)
        Assert.assertEquals(toolbar.menu.size(), 1)
        Assert.assertTrue(toolbar.menu.findItem(R.id.menu_main_addShop).isVisible)
        Assert.assertTrue(toolbar.menu.findItem(R.id.menu_main_addShop).isEnabled)
    }

    @Test
    fun MainActivity_Nav_Test() {
        var nav_view = mainActivity.findViewById<NavigationView>(R.id.nav_view)
        Assert.assertEquals(nav_view.menu.size(), 3)
        Assert.assertEquals(
            mainActivity.getString(R.string.Menu_Navigation_User_Setting)
            , nav_view.menu.findItem(R.id.menu_user_setting).title
        )
        Assert.assertEquals(
            mainActivity.getString(R.string.Menu_Navigation_User_Question)
            , nav_view.menu.findItem(R.id.menu_question).title
        )
        Assert.assertEquals(
            mainActivity.getString(R.string.Menu_Navigation_User_Logout)
            , nav_view.menu.findItem(R.id.menu_logout).title
        )

        nav_view.menu.performIdentifierAction(R.id.menu_question, 0)
        var userQuestionDialog = ShadowAlertDialog.getLatestAlertDialog()
        Assert.assertNotNull(userQuestionDialog)
        Assert.assertTrue(userQuestionDialog.isShowing)

        userQuestionDialog.dismiss()
        Assert.assertFalse(userQuestionDialog.isShowing)

    }

    @Test
    fun MainActivity_slidingTab_test() {
        var viewPager = mainActivity.findViewById<ViewPager>(R.id.viewPager)
        var slidingTab = mainActivity.findViewById<SlidingTabLayout>(R.id.slidingTab)

        var adapter = ViewPagerAdapter(
            mainActivity.supportFragmentManager
            , mainActivity.resources.getStringArray(R.array.ShopType)
        )
        viewPager.adapter = adapter
        slidingTab.setViewPager(viewPager)

        Assert.assertEquals(4, adapter.count)
        Assert.assertEquals(4, slidingTab.tabCount)
        Assert.assertEquals("早餐", adapter.title[0])
        Assert.assertEquals("晚餐", adapter.title[1])
        Assert.assertEquals("小吃", adapter.title[2])
        Assert.assertEquals("飲料", adapter.title[3])
    }

    @Test
    fun PermissionUnit_Test() {
        Assert.assertTrue(PermissionUnit.checkPermission(mainActivity))
    }

    @Test
    fun PickPictureUnit_Test() {
        var bitmap = Bitmap.createBitmap(1080, 720, Bitmap.Config.ARGB_8888)
        Assert.assertNotNull(PickPictureUnit.bitmapToByte(bitmap))
    }

    @Test
    fun VersionCheckUnit_Test(){
        Assert.assertEquals(180,VersionCheckUnit.VERSION_CHECK_NEW)
    }

    @Test
    fun GpsUnit_Test(){
        Assert.assertEquals(100,GpsUnit.GPS_UNIT_REQUESTCODE)
    }

    @Test
    fun FirebaseUnits_Test(){
        Assert.assertEquals(100,FirebaseUnits.AUTH_RQEUESTCODE)
    }


    @Test
    fun DetailShopActivity_Test() {

        var intent = Intent(mainActivity, DetailShopActivity::class.java).apply {
            this.putExtras(Bundle().apply {
                this.putString(DataConstant.SHOP_TYPE_TAG, Shop.BREAKFAST_SHOP)
                this.putInt(DataConstant.POSITION, 0)
            })
        }

        //這裡應該是沒intent 所以shop沒有初始化抱錯
        var detailShopActivity =
            Robolectric.buildActivity(DetailShopActivity::class.java,intent).create().get()

        Assert.assertEquals(Shop.BREAKFAST_SHOP, detailShopActivity.intent.extras.getString(DataConstant.SHOP_TYPE_TAG))
        Assert.assertEquals(0, detailShopActivity.intent.extras.getInt(DataConstant.POSITION))

        var detailShopTb=detailShopActivity.findViewById<Toolbar>(R.id.detailShopTb)
        Assert.assertEquals(2,detailShopTb.menu.size())

        var menu_detailShop_recommend=detailShopTb.menu.findItem(R.id.menu_detailshop_recommend)
        Assert.assertEquals(detailShopActivity.resources.getString(R.string.Menu_DetailShop_Recommend)
            ,menu_detailShop_recommend.title)
        Assert.assertTrue(menu_detailShop_recommend.isVisible)

        var menu_detailShop_search=detailShopTb.menu.findItem(R.id.menu_detailshop_search)
        Assert.assertEquals(detailShopActivity.resources.getString(R.string.Menu_DetailShop_Search)
            ,menu_detailShop_search.title)
        Assert.assertFalse(menu_detailShop_search.isVisible)

        Assert.assertEquals(detailShopActivity.resources.getString(R.string.ShopInformation),detailShopTb.title)
    }

    @Test
    fun AddCommentDialog_Test(){
        Assert.assertEquals("AddCommentDialog",AddCommentDialog.TAG)
        //Assert.assertEquals("MENU_ID",AddCommentDialog.MENU_ID)
        //Assert.assertEquals("COMMENT_SIZE",AddCommentDialog.COMMENT_SIZE)

        var addCommentDialog=AddCommentDialog.getInstace("0","0")

        //Assert.assertEquals("0",addCommentDialog.arguments?.getString(AddCommentDialog.MENU_ID))
        //Assert.assertEquals("0",addCommentDialog.arguments?.getString(AddCommentDialog.COMMENT_SIZE))
        Assert.assertNotNull(addCommentDialog)

        addCommentDialog.show(mainActivity.supportFragmentManager,AddCommentDialog.TAG)
        addCommentDialog.dismiss()
    }

}