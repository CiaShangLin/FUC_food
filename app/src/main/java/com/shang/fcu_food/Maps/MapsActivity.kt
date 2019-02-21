package com.shang.fcu_food.Maps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.DetailShop.DetailShopActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.GpsUnit
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.toast

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    companion object {
        val TAG: String = "MapsActivity"
        val LATLNG = "LATLNG"
        val REQUEST_CODE_LATLNG: Int = 200
    }


    private lateinit var mMap: GoogleMap
    private lateinit var mMapListener: MapListener

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

        val fcu = LatLng(24.178827, 120.646460)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu, 17f))

        mMapListener = MapListener(mMap, this@MapsActivity)

        when (intent.action) {
            //顯示店家
            DetailShopActivity::class.java.simpleName -> mMapListener.initShowShop(intent)
            //只為了取得經緯度
            else -> {
                mMapListener.initGetLocation(con,clickLottieView)
            }
        }

        mMap.setOnMyLocationButtonClickListener(mMapListener)
        mMapListener.mapUiSetting()

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
