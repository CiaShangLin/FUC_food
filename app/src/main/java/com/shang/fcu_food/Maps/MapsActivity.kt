package com.shang.fcu_food.Maps

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.DetailShop.DetailShopActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.GpsUnit
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    companion object {
        val LATLNG = "LATLNG"
        val REQUEST_CODE_LATLNG: Int = 200
    }

    val TAG: String = "MapsActivity"
    private lateinit var mMap: GoogleMap
    var position: Int = 0
    var shop_tag: String = BreakfastShop.tag
    var shopList: MutableList<Shop> = mutableListOf<Shop>()
    lateinit var map: Map

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

        map = Map(mMap, this@MapsActivity)

        when (intent.action) {
            DetailShopActivity::class.java.simpleName -> map.initShowShop(intent)
            else -> map.initGetLocation(con)
        }

        val fcu = LatLng(24.178827, 120.646460)
        mMap.addMarker(MarkerOptions().position(fcu).title("逢甲"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu, 17f))

        mMap.setOnMyLocationButtonClickListener(map)
        map.mapUiSetting()
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
