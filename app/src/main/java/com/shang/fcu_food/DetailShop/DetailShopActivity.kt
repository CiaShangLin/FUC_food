package com.shang.fcu_food.DetailShop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.AdmobUnit
import kotlinx.android.synthetic.main.activity_detail_shop.*
import org.jetbrains.anko.toast


class DetailShopActivity : AppCompatActivity() {

    private var position: Int = 0
    private var shop_tag: String = ""

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: DetailShopAdapter
    private lateinit var firebaseShop:FirebaseShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

        if (intent.extras != null) {
            position = intent.extras.getInt(DataConstant.POSITION)
            shop_tag = intent.extras.getString(DataConstant.SHOP_TYPE_TAG)
            firebaseShop = Shop.getShopType(shop_tag)!!
        }

        detailShopTb.inflateMenu(R.menu.menu_detailshop)
        detailShopTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_detailshop_search -> toast("功能尚未實作")
                R.id.menu_detailshop_recommend -> {
                    position = (Math.random() * (detailShopRecyc.adapter)?.itemCount!!).toInt()
                    adapter.recommend(linearLayoutManager, position)
                }
            }
            true
        }

        detailShopTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailShopTb.setNavigationOnClickListener {
            finish()
        }

        adapter = DetailShopAdapter(this, firebaseShop.getOption())
        detailShopRecyc.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailShopRecyc.layoutManager = linearLayoutManager

        var pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailShopRecyc)

       // AdmobUnit.getInstance(this)?.show(detailShopAdView)
        //AdmobUnit.getInstance(this)?.show(detailShopAdView2)
    }

    override fun onResume() {
        super.onResume()
        detailShopRecyc.smoothScrollToPosition(position)
        Log.d("TAG", shop_tag + " resume " + position)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        //確保切回來的是之前按的position
        position = linearLayoutManager?.findFirstVisibleItemPosition().toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        //放在onStop裡的話　跳轉回來時他不會定位
        adapter.stopListening()
    }

    fun recommend() {
        //var position=linearLayoutManager?.findFirstVisibleItemPosition().toInt()
        //var shop =(adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>)?.getItem(position)
        //detailShopRecyc.smoothScrollToPosition(position)
    }
}

