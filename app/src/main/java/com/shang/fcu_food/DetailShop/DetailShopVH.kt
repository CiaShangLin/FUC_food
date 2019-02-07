package com.shang.fcu_food.DetailShop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.AdView
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DetailMenu.DetailMenuActivity
import com.shang.fcu_food.Dialog.EditShopDialog
import com.shang.fcu_food.Dialog.ImageViewDialog
import com.shang.fcu_food.Maps.MapsActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.AdmobUnit
import com.shang.fcu_food.Unit.FileStorageUnit
import kotlinx.android.synthetic.main.cardview_detailshop.view.*

class DetailShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var shopNameTv = itemView.findViewById<TextView>(R.id.shopNameTv)
    var shopPhoneTv = itemView.findViewById<TextView>(R.id.shopPhoneTv)
    var shopOpenTv = itemView.findViewById<TextView>(R.id.shopOpenTv)
    var shopStarTv = itemView.findViewById<TextView>(R.id.shopStarTv)
    var shopMapIg = itemView.findViewById<ImageView>(R.id.shopMapIg)
    var shopMenu = itemView.findViewById<RecyclerView>(R.id.shopMenu)
    var shopPictureImg = itemView.findViewById<ImageView>(R.id.shopPictureImg)
    var shopAdView = itemView.findViewById<AdView>(R.id.shopAdView)
    var shopEditImg = itemView.findViewById<ImageView>(R.id.shopEditImg)

    fun bind(shop_tag: String, model: Shop, position: Int, activity: DetailShopActivity) {

        when (shop_tag) {
            BreakfastShop.tag -> model as BreakfastShop
            DinnerShop.tag -> model as DinnerShop
            SnackShop.tag -> model as SnackShop
            DrinkShop.tag -> model as DrinkShop
        }

        itemView.shopNameTv.text = model.name
        itemView.shopOpenTv.text = model.time
        itemView.shopStarTv.text = String.format("%.1f", model.star)
        itemView.shopPhoneTv.text = model.phone

        FileStorageUnit.ImageLoader(
            itemView.context, shop_tag, model.name, model.name
            , itemView.shopPictureImg, model.errorDrawable, RequestOptions()
        )

        itemView.shopMapIg.setOnClickListener {
            activity.startActivity(Intent(activity, MapsActivity::class.java).apply {
                this.action = activity.javaClass.simpleName
                this.putExtra(DataConstant.SHOP_TYPE_TAG, shop_tag)
                this.putExtra(DataConstant.POSITION, position)
            })
        }

        itemView.shopPictureImg.setOnClickListener {
            ImageViewDialog.getInstance(shop_tag, model.name, model.name)
                .show(activity.supportFragmentManager, ImageViewDialog.TAG)
        }

        itemView.shopMenu.layoutManager = GridLayoutManager(itemView.context, 2)
        itemView.shopMenu.adapter =
                SimpleMenuAdapter(
                    let { model.menu },
                    shop_tag,
                    model.name,
                    getItemClick(shop_tag, model.id.toString(), model.name, itemView.context)
                )

        itemView.shopEditImg.setOnClickListener {
            EditShopDialog.getInstance(model, shop_tag).show(activity.supportFragmentManager, EditShopDialog.TAG)
        }

        AdmobUnit.getInstance(itemView.context)?.show(itemView.shopAdView)
    }

    //傳遞shop的id和type 還有position
    fun getItemClick(tag: String, id: String, name: String, context: Context): OnItemClickHandler {
        var itemClick = object : OnItemClickHandler {
            override fun onItemClick(bundle: Bundle) {
                bundle.putString(DataConstant.SHOP_NAME, name)
                bundle.putString(DataConstant.SHOP_TYPE_TAG, tag)
                bundle.putString(DataConstant.SHOP_ID, id)
                var intent = Intent(context, DetailMenuActivity::class.java).apply { this.putExtras(bundle) }
                context.startActivity(intent)
            }
        }
        return itemClick
    }


}