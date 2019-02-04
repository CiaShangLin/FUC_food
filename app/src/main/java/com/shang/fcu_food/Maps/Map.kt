package com.shang.fcu_food.Maps

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class Map(var mMap: GoogleMap,var activity: MapsActivity) : GoogleMap.OnMapClickListener {
    val TAG: String = "MapsActivity"
    var position: Int = 0
    var shop_tag: String = BreakfastShop.tag
    var shopList = mutableListOf<Shop>()

    //只為了取得經緯度
    fun initGetLocation(con: View) {
        mMap.setOnMapClickListener(this)
        Snackbar.make(con, "點擊地圖店家的大概位置", Snackbar.LENGTH_INDEFINITE)
            .setAction("確認", View.OnClickListener { })
            .setActionTextColor(Color.YELLOW)
            .show()
    }

    fun initShowShop(intent: Intent) {
        if (intent != null) {
            position = intent.getIntExtra(DataConstant.POSITION, 0)
            shop_tag = intent.getStringExtra(DataConstant.SHOP_TYPE_TAG)
            Log.v(TAG, "position:$position shoptag:$shop_tag")
        }
        when (shop_tag) {
            BreakfastShop.tag -> shopList = DataBind.allBreakfastShop as MutableList<Shop>
            DinnerShop.tag -> shopList = DataBind.allDinnerShop as MutableList<Shop>
            SnackShop.tag -> shopList = DataBind.allSanckShop as MutableList<Shop>
            DrinkShop.tag -> shopList = DataBind.allDrinkShop as MutableList<Shop>
        }
        inputMarker()
    }

    fun inputMarker() {
        for (shop in shopList) {
            var markerOptions =
                MarkerOptions().position(shop.getLatLng()).title(shop.name)
            mMap.addMarker(markerOptions)
        }
    }

    fun vectorToBitmap(){

    }

    override fun onMapClick(latlng: LatLng?) {
        activity.alert("確定是這裡嗎?\n$latlng.", "確認地點") {
            yesButton {
                var intent = Intent().apply {
                    this.putExtra(MapsActivity.LATLNG, latlng)
                }
                activity.setResult(MapsActivity.REQUEST_CODE_LATLNG, intent)
                activity.finish()
            }
            noButton {

            }
        }.show()
    }


}
