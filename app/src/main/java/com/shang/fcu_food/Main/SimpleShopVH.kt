package com.shang.fcu_food.Main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.DetailShop.DetailShopActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FileStorageUnit
import kotlinx.android.synthetic.main.cardview_simpleshop.view.*

class SimpleShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var simpleShopImg: ImageView = itemView.findViewById<ImageView>(R.id.simpleShopImg)
    var simpleShopName: TextView = itemView.findViewById(R.id.simpleShopName)
    var simpleShopStar: TextView = itemView.findViewById(R.id.simpleShopStar)
    var simpleShopPositionTv: TextView = itemView.findViewById(R.id.simpleShopPositionTv)

    fun bind(position: Int, model: Shop) {

        itemView.simpleShopName.text = model.name
        itemView.simpleShopStar.text = String.format("%.1f", model.star)

        FileStorageUnit.ImageLoader(
            itemView.context,
            model.shop_tag,
            model.name,
            model.name,
            itemView.simpleShopImg,
            model.errorDrawable,
            RequestOptions().fitCenter()
        )

        FileStorageUnit.ImageLoader(
            itemView.context,
            itemView.simpleShopImg,
            model.getFile(itemView.context),
            model.getStorageRef(),
            model.errorDrawable,
            RequestOptions().fitCenter()
        )

        itemView.setOnClickListener {
            goDetailShop_Activity(itemView.context, model.shop_tag, position)
        }

        itemView.simpleShopPositionTv.setText((position + 1).toString())
    }

    fun goDetailShop_Activity(context: Context, shop_tag: String, position: Int) {
        var bundle = Bundle().apply {
            this.putString(DataConstant.SHOP_TYPE_TAG, shop_tag)
            this.putInt(DataConstant.POSITION, position)
        }
        var intent = Intent(context, DetailShopActivity::class.java).apply {
            this.putExtras(bundle)
        }
        context.startActivity(intent)
    }
}