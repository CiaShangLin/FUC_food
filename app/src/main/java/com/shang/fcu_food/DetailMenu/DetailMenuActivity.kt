package com.shang.fcu_food.DetailMenu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Data.menu.*
import com.shang.fcu_food.Data.shop.BreakfastShop
import com.shang.fcu_food.Data.shop.DinnerShop
import com.shang.fcu_food.Data.shop.DrinkShop
import com.shang.fcu_food.Data.shop.SnackShop
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.AdmobUnit
import kotlinx.android.synthetic.main.activity_detail_menu.*

class DetailMenuActivity : AppCompatActivity() {

    //店家類型TAG 店家的KEY 點選位置POSITION
    //update SHOP_TYPE_TAG+SHOP_ID+"menu"+menu_id+usercomment+"comment_size"
    companion object {
        val TAG = "DetailMenuActivity"

        var shop_type_tag: String = ""
        var shop_id: String = ""
        var position: Int = 0
        var shop_name: String = ""
    }

    lateinit var menu: Menu
    lateinit var adapter: FirebaseRecyclerAdapter<Menu, DetailMenuVH>
    lateinit var options: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        var intent = intent.extras
        if (intent != null) {
            shop_name = intent.getString(DataConstant.SHOP_NAME)
            shop_type_tag = intent.getString(DataConstant.SHOP_TYPE_TAG)
            shop_id = intent.getString(DataConstant.SHOP_ID)
            position = intent.getInt(DataConstant.POSITION)
        }

        detailMenuTb.inflateMenu(R.menu.menu_detailmenu)
        detailMenuTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailMenuTb.setNavigationOnClickListener {
            finish()
        }
        detailMenuTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_detailmenu_addMenu ->
                    AddMenuDialog.getInstance(shop_name).show(supportFragmentManager, AddMenuDialog.TAG)
                R.id.menu_detailmenu_recommend -> recommend()
            }
            true
        }

        menu = when (shop_type_tag) {
            BreakfastShop.tag -> BreakfastMenu()
            DinnerShop.tag -> DinnerMenu()
            DrinkShop.tag -> DrinkMenu()
            SnackShop.tag -> SnackMenu()
            else -> Menu()
        }

        adapter = DetailMenuAdapter(this@DetailMenuActivity,menu.getOption(shop_type_tag, shop_id)!!)

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailMenuRecyc.layoutManager = layoutManager
        detailMenuRecyc.adapter = adapter

        var pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailMenuRecyc)

        //AdmobUnit.getInstance(this)?.show(detailMenuAdView)
    }

    override fun onResume() {
        super.onResume()
        detailMenuRecyc.smoothScrollToPosition(position)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun recommend() { //隨機推薦
        //var position=linearLayoutManager?.findFirstVisibleItemPosition().toInt()
        //var shop =(adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>)?.getItem(position)
        position = (Math.random() * (detailMenuRecyc.adapter?.itemCount!!)).toInt()
        //detailMenuRecyc.smoothScrollToPosition(position)
        (detailMenuRecyc.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)

    }

}
