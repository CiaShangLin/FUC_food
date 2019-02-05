package com.shang.fcu_food.DetailShop

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R

class DetailShopAdapter(
    var shop_tag: String, var activity: DetailShopActivity,
    options: FirebaseRecyclerOptions<Shop>
) : FirebaseRecyclerAdapter<Shop, DetailShopVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailShopVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_detailshop, parent, false)
        return DetailShopVH(view)
    }

    override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Shop) {
        holder.bind(shop_tag, model ,position, activity)
    }

    //隨機推薦
    fun recommend(manager: LinearLayoutManager, position: Int) {
        manager.scrollToPositionWithOffset(position, 0)
    }


}