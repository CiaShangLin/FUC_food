package com.shang.fcu_food.Maps

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.Unit.GpsUnit
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class Map(var mMap: GoogleMap, var activity: MapsActivity) :
    GoogleMap.OnMapClickListener,GoogleMap.OnMyLocationButtonClickListener {


    val TAG: String = "MapsActivity"


    //只為了取得經緯度
    fun initGetLocation(con: View) {
        mMap.setOnMapClickListener(this)
        Snackbar.make(con, "點擊地圖店家的大概位置", Snackbar.LENGTH_INDEFINITE)
            .setAction("確認", View.OnClickListener { })
            .setActionTextColor(Color.YELLOW)
            .show()
    }

    //顯示店家Marker
    fun initShowShop(intent: Intent) {
        var position: Int = 0
        var shop_tag: String = ""
        var shopList = mutableListOf<Shop>()

        if (intent != null) {
            position = intent.getIntExtra(DataConstant.POSITION, 0)
            shop_tag = intent.getStringExtra(DataConstant.SHOP_TYPE_TAG)
            Log.v(TAG, "position:$position shoptag:$shop_tag")
        }

        when (shop_tag) {
            BreakfastShop.tag -> shopList = BreakfastShop.allBreakfastShop.toMutableList()
            DinnerShop.tag -> shopList = DinnerShop.allDinnerShop.toMutableList()
            DrinkShop.tag -> shopList = DrinkShop.allDrinkShop.toMutableList()
            SnackShop.tag -> shopList = SnackShop.allSnackShop.toMutableList()
        }

        inputMarker(shopList)
    }

    private fun inputMarker(shopList:MutableList<Shop>) { //載入店家marker
        Log.d("TAG S","${shopList.get(0) is BreakfastShop}")
        Log.d("TAG S","${shopList.get(0) is DinnerShop}")
        Log.d("TAG S","${shopList.get(0) is DrinkShop}")
        Log.d("TAG S","${shopList.get(0) is SnackShop}")

        for (shop in shopList) {
            var markerOptions =
                MarkerOptions().position(shop.getLatLng()).title(shop.name)
            mMap.addMarker(markerOptions)
        }
    }

    private fun vectorToBitmap() {

    }

    @SuppressLint("MissingPermission")
    fun mapUiSetting(){
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.isMyLocationEnabled = true
    }

    override fun onMyLocationButtonClick(): Boolean {
        GpsUnit.OpenGps(activity)
        return false
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
