package com.shang.fcu_food.DetailShop

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.DetailMenu.DetailMenuActivity
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_detailshop.view.*

class DetailShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var shopNameTv = itemView.findViewById<TextView>(R.id.shopNameTv)
    var shopPhoneTv = itemView.findViewById<TextView>(R.id.shopPhoneTv)
    var shopOpenTv = itemView.findViewById<TextView>(R.id.shopOpenTv)
    var shopStarTv = itemView.findViewById<TextView>(R.id.shopStarTv)
    var shopMapImg = itemView.findViewById<ImageView>(R.id.shopMapIg)
    var shopMenu = itemView.findViewById<RecyclerView>(R.id.shopMenu)

    fun bind(tag: String, itemView: DetailShopVH, model: Shop, activity: Activity) {
        when (tag) {
            BreakfastShop.tag -> model as BreakfastShop
            DinnerShop.tag -> model as DinnerShop
            SnackShop.tag -> model as SnackShop
            DrinkShop.tag -> model as DrinkShop
        }
        itemView.shopNameTv.setText(model.name)
        itemView.shopOpenTv.setText(model.time)
        itemView.shopStarTv.setText(model.getStars().toString())
        itemView.shopPhoneTv.setText(model.phone)

        itemView.shopMenu.layoutManager = GridLayoutManager(activity, 2)
        itemView.shopMenu.adapter =
                SimpleMenuAdapter(let { model.menu }, getItemClick(tag, model.id.toString(), activity))
    }

    fun getItemClick(tag: String, id: String, activity: Activity): OnItemClickHandler {
        var itemClick = object : OnItemClickHandler {
            override fun onItemClick(bundle: Bundle) {
                bundle.putString(DetailMenuActivity.SHOP_TYPE_TAG, tag)
                bundle.putString(DetailMenuActivity.SHOP_ID, id)
                var intent = Intent(activity, DetailMenuActivity::class.java).apply { this.putExtras(bundle) }
                activity.startActivity(intent)
            }
        }
        return itemClick
    }


}