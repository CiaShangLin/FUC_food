package com.shang.fcu_food.Main

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.DetailShop.DetailShopVH
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class SimpleShopAdapter(options: FirebaseRecyclerOptions<Shop>, activity: FragmentActivity, tag:String) :
    FirebaseRecyclerAdapter<Shop,SimpleShopVH>(options){

    lateinit var activity:FragmentActivity
    lateinit var tag:String
    init {
        this.activity=activity
        this.tag=tag
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SimpleShopVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_simpleshop, parent, false)
        return SimpleShopVH(view)
    }

    override fun onBindViewHolder(holder: SimpleShopVH, position: Int, model: Shop) {
        holder.bind(holder,position,model,activity,tag)
    }


}