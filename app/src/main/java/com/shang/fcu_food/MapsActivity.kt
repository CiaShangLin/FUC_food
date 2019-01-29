package com.shang.fcu_food

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapClickListener {

    companion object {
        val LATLNG="LATLNG"
        val REQUEST_CODE_LATLNG:Int=200
    }
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)

        // Add a marker in Sydney and move the camera
        val fcu = LatLng(24.178827, 120.646460)
        mMap.addMarker(MarkerOptions().position(fcu).title("逢甲"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fcu,17f))

    }

    override fun onMapClick(latlng: LatLng?) {
        alert("確定是這裡嗎?\n$latlng.","確認地點"){
            yesButton {
                var intent=Intent().apply {
                    this.putExtra(LATLNG,latlng)
                }
                setResult(REQUEST_CODE_LATLNG,intent)
                finish()
            }
            noButton {

            }
        }.show()
    }


}
