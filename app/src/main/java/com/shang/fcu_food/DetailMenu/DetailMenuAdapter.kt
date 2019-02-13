package com.shang.fcu_food.DetailMenu

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.R

class DetailMenuAdapter(var activity: DetailMenuActivity, options: FirebaseRecyclerOptions<Menu>) :
    FirebaseRecyclerAdapter<Menu, DetailMenuVH>(options) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailMenuVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_detailmenu, p0, false)
        return DetailMenuVH(view)
    }

    override fun onBindViewHolder(holder: DetailMenuVH, position: Int, model: Menu) {
        Log.d("TAG M", (model is Menu).toString())
        Log.d("TAG B", (model is BreakfastMenu).toString())
        Log.d("TAG Di", (model is DinnerMenu).toString())
        Log.d("TAG Dr", (model is DrinkMenu).toString())
        Log.d("TAG B", (model is SnackMenu).toString())
        holder.bind(position, model,activity)
    }

}