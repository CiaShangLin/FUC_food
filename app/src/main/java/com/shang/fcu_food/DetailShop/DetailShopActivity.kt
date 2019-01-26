package com.shang.fcu_food.DetailShop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.ads.AdSize
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Dialog.AddShopDialog
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_detail_shop.*
import org.jetbrains.anko.toast


class DetailShopActivity : AppCompatActivity() {

    var position: Int = 0
    var shop_tag: String = ""

    lateinit var adapter: Any
    lateinit var options: Any


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

        if (intent.extras != null) {
            position = intent.extras.getInt(DataConstant.POSITION)
            shop_tag = intent.extras.getString(DataConstant.SHOP_TYPE_TAG)
        }

        detailShopTb.inflateMenu(R.menu.menu_detailshop)
        detailShopTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> toast("功能尚未實作")
            }
            true
        }
        detailShopTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailShopTb.setNavigationOnClickListener {
            finish()
        }

        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
        when (shop_tag) {
            BreakfastShop.tag ->
                options = FirebaseRecyclerOptions.Builder<BreakfastShop>().setQuery(query, BreakfastShop::class.java)
                    .build()
            DinnerShop.tag ->
                options = FirebaseRecyclerOptions.Builder<DinnerShop>().setQuery(query, DinnerShop::class.java).build()
            SnackShop.tag ->
                options = FirebaseRecyclerOptions.Builder<SnackShop>().setQuery(query, SnackShop::class.java).build()
            DrinkShop.tag ->
                options = FirebaseRecyclerOptions.Builder<DrinkShop>().setQuery(query, DrinkShop::class.java).build()
        }

        adapter = object : FirebaseRecyclerAdapter<Shop, DetailShopVH>(options as FirebaseRecyclerOptions<Shop>) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailShopVH {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_detailshop, parent, false)
                return DetailShopVH(view)
            }

            override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Shop) {
                holder.bind(shop_tag, model)
            }
        }

        detailShopRecyc.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailShopRecyc.adapter = adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>

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

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        //放在onStop裡的話　跳轉回來時他不會定位
        (adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>).stopListening()
    }
}

