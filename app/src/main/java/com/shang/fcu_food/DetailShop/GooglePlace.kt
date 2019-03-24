package com.shang.fcu_food.DetailShop

import android.util.Log
import com.google.gson.Gson
import com.shang.fcu_food.Data.DetailPlace
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.*
import java.io.IOException
import java.lang.NullPointerException


class GooglePlace {

    companion object {
        private var googlePlace: GooglePlace? = null
        fun getInstance(): GooglePlace {
            if (googlePlace == null) {
                googlePlace =
                    GooglePlace()
            }
            return googlePlace as GooglePlace
        }
    }

    fun getGooglePlaceData(place_id: String, observer: Observer<DetailPlace>) {
        val KEY = "key=AIzaSyBw-VTxtdZFwJMqwW4ClRF25lbEKQZZJdE"
        val LANGUAGE = "language=zh-TW"
        val FIELDS = "fields=reviews"
        val URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=$place_id&$FIELDS&$LANGUAGE&$KEY"

        var okHttpClient = OkHttpClient()
        var request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var body = response.body()?.string()
                var detailPlace = Gson().fromJson<DetailPlace>(body, DetailPlace::class.java)

                Observable.create(object : ObservableOnSubscribe<DetailPlace> {
                    override fun subscribe(emitter: ObservableEmitter<DetailPlace>) {
                        if (detailPlace.status.equals("OK"))
                            emitter.onNext(detailPlace)
                        else emitter.onError(NullPointerException())
                        emitter.onComplete()
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
            }

            override fun onFailure(call: Call, e: IOException) {
                Observable.create(object : ObservableOnSubscribe<DetailPlace> {
                    override fun subscribe(emitter: ObservableEmitter<DetailPlace>) {
                        emitter.onError(e)
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
            }

        })


    }


}
