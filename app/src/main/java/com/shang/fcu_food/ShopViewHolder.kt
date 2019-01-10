package com.shang.fcu_food

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ShopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var img:ImageView=itemView.findViewById<ImageView>(R.id.breakfastImg)
    var name:TextView=itemView.findViewById(R.id.breakfastName)
    var address:TextView=itemView.findViewById(R.id.breakfastStar)
}