package com.shang.fcu_food.Maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.GpsUnit
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.dimen
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MapListener(var mMap: GoogleMap, var activity: MapsActivity) :
    GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {


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

    private fun inputMarker(shopList: MutableList<Shop>) { //載入店家marker
        for (shop in shopList) {
            var markerOptions =
                MarkerOptions().position(shop.getLatLng())
                    .title(shop.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(vectorToBitmap(activity,R.drawable.ic_shop)))
                    .snippet(shop.id.toString())
            mMap.addMarker(markerOptions)
        }
        info(shopList)
    }

    private fun info(shopList: MutableList<Shop>){
        mMap.setInfoWindowAdapter(MyInfoWindowAdapter(activity,shopList))
    }

    //向量圖轉成Bitmap
    private fun vectorToBitmap(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        var bitmap =
            Bitmap.createBitmap(drawable?.intrinsicWidth!!, drawable?.intrinsicHeight!!, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, context.dimen(R.dimen.googlemap_icon_width)
            , context.dimen(R.dimen.googlemap_icon_height))
        drawable.draw(canvas)

        return bitmap
    }

    @SuppressLint("MissingPermission")
    fun mapUiSetting() {
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
            noButton {}
        }.show()
    }


}