package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.R

class DetailMenuAdapter(var activity: DetailMenuActivity, options: FirebaseRecyclerOptions<Menu>) :
    FirebaseRecyclerAdapter<Menu, DetailMenuVH>(options) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailMenuVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_detailmenu, p0, false)
        return DetailMenuVH(view)
    }

    override fun onBindViewHolder(holder: DetailMenuVH, position: Int, model: Menu) {
        holder.bind(position, model, activity)
    }

    //隨機推薦
    fun recommend(manager: LinearLayoutManager, position: Int) {
        manager.scrollToPositionWithOffset(position, 0)
        //var position=linearLayoutManager?.findFirstVisibleItemPosition().toInt()
        //var shop =(adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>)?.getItem(position)
        //detailMenuRecyc.smoothScrollToPosition(position)
    }

}