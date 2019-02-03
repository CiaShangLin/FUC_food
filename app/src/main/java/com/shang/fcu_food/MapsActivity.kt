package com.shang.fcu_food

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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.DetailShop.DetailShopActivity
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    companion object {
        val LATLNG = "LATLNG"
        val REQUEST_CODE_LATLNG: Int = 200
    }

    val TAG:String="MapsActivity"
    private lateinit var mMap: GoogleMap
    var position: Int = 0
    var shop_tag: String = BreakfastShop.tag
    var shopList= mutableListOf<Shop>()

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

        // Add a marker in Sydney and move the camera
        val fcu = LatLng(24.178827, 120.646460)
        mMap.addMarker(MarkerOptions().position(fcu).title("逢甲"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu, 17f))


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
            Log.v(TAG,"position:$position shoptag:$shop_tag")
        }
        when(shop_tag){

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


}
