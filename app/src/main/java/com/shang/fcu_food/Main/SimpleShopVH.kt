package com.shang.fcu_food.Main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DetailShop.DetailShopActivity
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_simpleshop.view.*

class SimpleShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img: ImageView = itemView.findViewById<ImageView>(R.id.simpleShopImg)
    var name: TextView = itemView.findViewById(R.id.simpleShopName)
    var star: TextView = itemView.findViewById(R.id.simpleShopStar)

    fun bind(holder: SimpleShopVH, position: Int, model: Shop, activity: FragmentActivity, tag: String) {
        when (tag) {
            BreakfastShop.tag -> model as BreakfastShop
            DinnerShop.tag -> model as DinnerShop
            SnackShop.tag -> model as SnackShop
            DrinkShop.tag -> model as DrinkShop
        }
        holder.itemView.simpleShopName.text = model.name
        holder.itemView.simpleShopStar.text = model.getStars().toString()
        FirebaseUnits.storage_loadImg(activity!!, holder.itemView.simpleShopImg, tag, model.name)
        holder.itemView.setOnClickListener {
            goDetailShop_Activity(activity, tag, position)
        }
    }

    fun goDetailShop_Activity(activity: FragmentActivity, tag: String, position: Int) {
        var bundle = Bundle().apply {
            this.putString(DetailShopActivity.TAG, tag)
            this.putInt(DetailShopActivity.POSITION, position)
        }
        var intent = Intent(activity, DetailShopActivity::class.java).apply {
            this.putExtras(bundle)
        }
        activity?.startActivity(intent)
    }
}