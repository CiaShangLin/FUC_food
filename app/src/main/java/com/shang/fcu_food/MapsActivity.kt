package com.shang.fcu_food

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DetailShop.DetailShopActivity
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    companion object {
        val LATLNG = "LATLNG"
        val REQUEST_CODE_LATLNG: Int = 200
    }

    val TAG: String = "MapsActivity"
    private lateinit var mMap: GoogleMap
    var position: Int = 0
    var shop_tag: String = BreakfastShop.tag
    var shopList = mutableListOf<Shop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        when (intent.action) {
            DetailShopActivity::class.java.simpleName -> {
                initShowShop()
            }
            else -> {
                initGetLocation()
            }
        }

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.isMyLocationEnabled = true

        // Add a marker in Sydney and move the camera
        val fcu = LatLng(24.178827, 120.646460)
        mMap.addMarker(MarkerOptions().position(fcu).title("逢甲"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu, 17f))


        mMap.setOnMyLocationButtonClickListener(object : GoogleMap.OnMyLocationButtonClickListener {
            override fun onMyLocationButtonClick(): Boolean {
                GpsUnit.OpenGps(this@MapsActivity)
                return false
            }
        })

    }

    //只為了取得經緯度
    fun initGetLocation() {
        mMap.setOnMapClickListener(this)
        Snackbar.make(con, "點擊地圖店家的大概位置", Snackbar.LENGTH_INDEFINITE)
            .setAction("確認", View.OnClickListener { })
            .setActionTextColor(Color.YELLOW)
            .show()
    }

    fun initShowShop() {
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
    }


    override fun onMapClick(latlng: LatLng?) {
        alert("確定是這裡嗎?\n$latlng.", "確認地點") {
            yesButton {
                var intent = Intent().apply {
                    this.putExtra(LATLNG, latlng)
                }
                setResult(REQUEST_CODE_LATLNG, intent)
                finish()
            }
            noButton {

            }
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GpsUnit.GPS_UNIT_REQUESTCODE -> {
                if (GpsUnit.GpsIsOpen(this)) {
                    toast("開啟成功")
                } else {
                    toast("開啟失敗")
                }
            }
        }

    }
}
