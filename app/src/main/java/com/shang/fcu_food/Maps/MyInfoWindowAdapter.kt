package com.shang.fcu_food.Maps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.R

class MyInfoWindowAdapter(var context: Context,var shopList: MutableList<Shop>) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {
        var view=LayoutInflater.from(context).inflate(R.layout.infowindow_layout,null,false)
        var shopNameTv=view.findViewById<TextView>(R.id.infoShopNameTv)
        var openTv=view.findViewById<TextView>(R.id.infoOpenTv)
        var phoneTv=view.findViewById<TextView>(R.id.infoPhoneTv)
        var starTv=view.findViewById<TextView>(R.id.infoStarTv)

        Log.d("TAG","id:${marker?.snippet}")
        var shop=shopList.get(marker?.snippet!!.toInt())
        shopNameTv.setText(shop.name)
        openTv.setText(shop.time)
        phoneTv.setText(shop.phone)
        starTv.setText(String.format("%.2f",shop.star))

        return view

    }

    override fun getInfoWindow(p0: Marker?): View? = null

}