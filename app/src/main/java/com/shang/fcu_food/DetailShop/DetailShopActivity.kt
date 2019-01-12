package com.shang.fcu_food.DetailShop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_detail_shop.*

class DetailShopActivity : AppCompatActivity() {

    companion object {
        val TAG = "TAG"     //傳遞店家TAG參數
        val POSITION = "POSITION"  //傳遞哪一個位置
    }

    var position:Int=0
    var tag:String=""
    lateinit var adapter:Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

        detailShopTb.setTitle(R.string.ShopInformation)
        detailShopTb.inflateMenu(R.menu.menu_detailshop)

        var query = FirebaseDatabase.getInstance().getReference().child("breakfast")

        var options = FirebaseRecyclerOptions
            .Builder<BreakfastShop>()
            .setQuery(query, BreakfastShop::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<BreakfastShop, DetailShopVH>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailShopVH {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_detailshop, parent, false)
                return DetailShopVH(view)
            }

            override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: BreakfastShop) {

            }
        }
        detailShopRecyc.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailShopRecyc.adapter= adapter as FirebaseRecyclerAdapter<BreakfastShop, DetailShopVH>
        (adapter as FirebaseRecyclerAdapter<BreakfastShop, DetailShopVH>).startListening()

    }
}
