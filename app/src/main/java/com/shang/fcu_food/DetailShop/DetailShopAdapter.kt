package com.shang.fcu_food.DetailShop

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.menu.*
import com.shang.fcu_food.Data.shop.DinnerShop
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.Dialog.EditShopDialog
import com.shang.fcu_food.Dialog.ImageViewDialog
import com.shang.fcu_food.Maps.MapsActivity
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.toolbar_layout.*

class DetailShopAdapter(
    var activity: DetailShopActivity, options: FirebaseRecyclerOptions<Shop>
) : FirebaseRecyclerAdapter<Shop, DetailShopVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailShopVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_detailshop, parent, false)
        return DetailShopVH(view)
    }

    override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Shop) {
        holder.bind(model, position, activity)
    }

    private fun getPositionItem(position: Int): Shop = this.getItem(position)

    //隨機推薦
    fun recommend(manager: LinearLayoutManager, position: Int) {
        manager.scrollToPositionWithOffset(position, 0)
    }

    fun detailShopTbClick(position: Int, menu_id: Int) {
        var model = getPositionItem(position)
        when (menu_id) {
            R.id.menu_detailshop_search -> { }
            //顯示菜單
            R.id.menu_detailshop_showMenu -> {
                ImageViewDialog.getInstance(model.shop_tag, model.name, "菜單")
                    .show(activity.supportFragmentManager, ImageViewDialog.TAG)
            }
            //Google地圖
            R.id.menu_detailshop_googleMap -> {
                activity.startActivity(Intent(activity, MapsActivity::class.java).apply {
                    this.action = activity.javaClass.simpleName
                    this.putExtra(DataConstant.SHOP_TYPE_TAG, model.shop_tag)
                    this.putExtra(DataConstant.POSITION, position)
                })
            }
            //新增菜單
            R.id.menu_detailshop_addMenu -> {
                AddMenuDialog.getInstance(model.name).show(activity.supportFragmentManager, AddMenuDialog.TAG)
            }
            //修改店家
            R.id.menu_detailshop_editShop -> {
                EditShopDialog.getInstance(model).show(activity.supportFragmentManager, EditShopDialog.TAG)
            }
        }
    }


}