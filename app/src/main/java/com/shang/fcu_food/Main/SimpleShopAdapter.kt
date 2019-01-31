package com.shang.fcu_food.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.R

class SimpleShopAdapter(options: FirebaseRecyclerOptions<Shop>, tag: String) :
    FirebaseRecyclerAdapter<Shop, SimpleShopVH>(options) {
    lateinit var tag: String

    init {
        this.tag = tag
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SimpleShopVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_simpleshop, parent, false)
        return SimpleShopVH(view)
    }

    override fun onBindViewHolder(holder: SimpleShopVH, position: Int, model: Shop) {
        holder.bind(position, model, tag)
    }

}