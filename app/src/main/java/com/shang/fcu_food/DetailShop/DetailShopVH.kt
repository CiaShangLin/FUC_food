package com.shang.fcu_food.DetailShop

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_detailshop.view.*

class DetailShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var shopNameTv=itemView.findViewById<TextView>(R.id.shopNameTv)
    var shopPhoneTv=itemView.findViewById<TextView>(R.id.shopPhoneTv)
    var shopOpenTv=itemView.findViewById<TextView>(R.id.shopOpenTv)
    var shopStarTv=itemView.findViewById<TextView>(R.id.shopStarTv)
    var shopMapImg=itemView.findViewById<ImageView>(R.id.shopMapIg)



}