package com.shang.fcu_food.Main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.R

class SimpleShopVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    var img:ImageView=itemView.findViewById<ImageView>(R.id.breakfastImg)
    var name:TextView=itemView.findViewById(R.id.breakfastName)
    var address:TextView=itemView.findViewById(R.id.breakfastStar)
}