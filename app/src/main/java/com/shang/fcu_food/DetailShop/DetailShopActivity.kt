package com.shang.fcu_food.DetailShop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_detail_shop.*
import org.jetbrains.anko.toast


class DetailShopActivity : AppCompatActivity() {

    var position: Int = 0
    var shop_tag: String = ""

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: DetailShopAdapter
    lateinit var options: Any
    lateinit var shop:Shop


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

        if (intent.extras != null) {
            position = intent.extras.getInt(DataConstant.POSITION)
            shop_tag = intent.extras.getString(DataConstant.SHOP_TYPE_TAG)
            when (shop_tag) {
                BreakfastShop.tag -> shop=BreakfastShop()
                DinnerShop.tag -> shop=DinnerShop()
                SnackShop.tag -> shop=SnackShop()
                DrinkShop.tag -> shop=DrinkShop()
            }
        }

        detailShopTb.inflateMenu(R.menu.menu_detailshop)
        detailShopTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_detailshop_search -> toast("功能尚未實作")
                R.id.menu_detailshop_recommend -> {
                    position = (Math.random() * (detailShopRecyc.adapter)?.itemCount!!).toInt()
                    adapter.recommend(linearLayoutManager,position)
                }
            }
            true
        }
        detailShopTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailShopTb.setNavigationOnClickListener {
            finish()
        }

        adapter=DetailShopAdapter(shop_tag,this,shop.getOption()!!)
        detailShopRecyc.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailShopRecyc.layoutManager = linearLayoutManager


        var pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailShopRecyc)

    }

    override fun onResume() {
        super.onResume()
        detailShopRecyc.smoothScrollToPosition(position)
        Log.d("TAG", shop_tag + " resume " + position)
    }

    override fun onStart() {
        super.onStart()
        (adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>).startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        //放在onStop裡的話　跳轉回來時他不會定位
        (adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>).stopListening()
    }

    fun recommend() {
        //var position=linearLayoutManager?.findFirstVisibleItemPosition().toInt()
        //var shop =(adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>)?.getItem(position)
        //detailShopRecyc.smoothScrollToPosition(position)
    }
}

