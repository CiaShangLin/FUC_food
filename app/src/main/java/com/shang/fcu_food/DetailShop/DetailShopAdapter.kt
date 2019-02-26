package com.shang.fcu_food.DetailShop

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.menu.*
import com.shang.fcu_food.Data.shop.DinnerShop
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.R

class DetailShopAdapter(var activity: DetailShopActivity, options: FirebaseRecyclerOptions<Shop>
) : FirebaseRecyclerAdapter<Shop, DetailShopVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailShopVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_detailshop, parent, false)
        return DetailShopVH(view)
    }

    override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Shop) {
        holder.bind(model ,position, activity)
    }

    //隨機推薦
    fun recommend(manager: LinearLayoutManager, position: Int) {
        manager.scrollToPositionWithOffset(position, 0)
    }


}