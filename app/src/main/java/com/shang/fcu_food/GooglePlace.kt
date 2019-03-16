package com.shang.fcu_food

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class GooglePlace {

    interface ChangeGooglePlace {
        fun changeGooglePlace(reviews: DetailPlace?)
    }

    companion object {

        private var googlePlace: GooglePlace? = null
        private var ChangeGooglePlace: ChangeGooglePlace? = null
        fun getInstance(changeGooglePlace: ChangeGooglePlace): GooglePlace {
            this.ChangeGooglePlace = changeGooglePlace
            if (googlePlace == null) {
                googlePlace = GooglePlace()
            }
            return googlePlace as GooglePlace
        }
    }

    fun getGooglePlaceData(place_id: String) {
        val KEY = "key=AIzaSyBw-VTxtdZFwJMqwW4ClRF25lbEKQZZJdE"
        val LANGUAGE = "language=zh-TW"
        val FIELDS = "fields=reviews"
        var URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=$place_id&$FIELDS&$LANGUAGE&$KEY"

        var okHttpClient = OkHttpClient()
        var request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var body = response.body()?.string()
                var detailPlace = Gson().fromJson<DetailPlace>(body, DetailPlace::class.java)
                if(detailPlace.status.equals("OK")){
                    ChangeGooglePlace?.changeGooglePlace(detailPlace)
                }else{
                    ChangeGooglePlace?.changeGooglePlace(null)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                ChangeGooglePlace?.changeGooglePlace(null)
            }

        })
    }


}
