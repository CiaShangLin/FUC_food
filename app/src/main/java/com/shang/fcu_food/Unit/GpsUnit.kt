package com.shang.fcu_food.Unit

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import com.shang.fcu_food.Maps.MapsActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class GpsUnit {

    companion object {
        val GPS_UNIT_REQUESTCODE:Int=100

        //檢查GPS狀態
        fun GpsIsOpen(context: Context):Boolean{
            var locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            return gps
        }

        //開啟GPS
        fun OpenGps(activity: MapsActivity){
            if(!GpsIsOpen(activity)){
                var intent=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivityForResult(intent, GPS_UNIT_REQUESTCODE)
            }
        }

    }
}