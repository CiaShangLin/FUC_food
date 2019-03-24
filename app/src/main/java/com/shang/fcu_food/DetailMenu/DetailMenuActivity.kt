package com.shang.fcu_food.DetailMenu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Data.menu.*
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.Dialog.EditMenuDialog
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.AdmobUnit
import kotlinx.android.synthetic.main.activity_detail_menu.*

class DetailMenuActivity : AppCompatActivity() {

    //店家類型TAG 店家的KEY 點選位置POSITION
    //update SHOP_TYPE_TAG+SHOP_ID+"menu"+menu_id+usercomment+"comment_size"

    val TAG = "DetailMenuActivity"
    lateinit var shop_type_tag: String
    var shop_id: Int = -1
    var position: Int = 0
    lateinit var shop_name: String


    lateinit var firebaseMenu: FirebaseMenu
    lateinit var adapter: DetailMenuAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        var intent = intent.extras
        if (intent != null) {
            shop_name = intent.getString(DataConstant.SHOP_NAME)
            shop_type_tag = intent.getString(DataConstant.SHOP_TYPE_TAG)
            shop_id = intent.getInt(DataConstant.SHOP_ID)
            position = intent.getInt(DataConstant.POSITION)
            Log.d(TAG,"shop_name:$shop_name shop_type_tag:$shop_type_tag shop_id:$shop_id position:$position")
        }

        detailMenuTb.inflateMenu(R.menu.menu_detailmenu)
        detailMenuTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailMenuTb.setNavigationOnClickListener {
            finish()
        }
        detailMenuTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_detailmenu_recommend -> {
                    position = (Math.random() * (detailMenuRecyc.adapter?.itemCount!!)).toInt()
                    adapter.recommend(linearLayoutManager, position)
                }
                R.id.menu_detailmenu_editMenu -> {
                    var menu = adapter.getItem(linearLayoutManager?.findFirstVisibleItemPosition().toInt())
                    var editMenuDialog = EditMenuDialog.getInstance(shop_name, menu.name, menu.price)
                    editMenuDialog.show(supportFragmentManager, EditMenuDialog.TAG)
                }
            }
            true
        }

        firebaseMenu = when (shop_type_tag) {
            Shop.BREAKFAST_SHOP -> BreakfastMenu()
            Shop.DINNER_SHOP -> DinnerMenu()
            Shop.DRINK_SHOP -> DrinkMenu()
            Shop.SNACK_SHOP -> SnackMenu()
            else -> BreakfastMenu()
        }

        adapter = DetailMenuAdapter(this, firebaseMenu.getOption(shop_type_tag, shop_name, shop_id)!!)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailMenuRecyc.layoutManager = linearLayoutManager
        detailMenuRecyc.adapter = adapter

        var pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailMenuRecyc)

        //AdmobUnit.getInstance(this)?.show(detailMenuAdView)
        //AdmobUnit.getInstance(this)?.show(detailMenuAdView2)
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

}
