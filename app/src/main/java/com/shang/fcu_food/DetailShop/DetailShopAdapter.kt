package com.shang.fcu_food.DetailShop

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.R

class DetailShopAdapter(var shop_tag: String, options: FirebaseRecyclerOptions<Any>) :
    FirebaseRecyclerAdapter<Any, DetailShopVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailShopVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_detailshop, parent, false)
        return DetailShopVH(view)
    }

    override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Any) {
        Log.d("TAG",(model is BreakfastShop).toString())
        Log.d("TAG",(model is DinnerShop).toString())
        Log.d("TAG",(model is SnackShop).toString())
        Log.d("TAG",(model is DrinkShop).toString())
    }


}