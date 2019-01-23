package com.shang.fcu_food.Unit

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

class AdmobUnit {
    companion object {
        var admobUnit: AdmobUnit?=null
        var mInterstitialAd:InterstitialAd?=null
        fun getInstance(context:Context): AdmobUnit? {
            if(admobUnit ==null){
                admobUnit = AdmobUnit()
                MobileAds.initialize(context,"ca-app-pub-3596318314144695~8650129956")
                mInterstitialAd = InterstitialAd(context)
                mInterstitialAd?.setAdUnitId("ca-app-pub-3940256099942544/1033173712")
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }
            return admobUnit
        }
    }

    fun show(adView:AdView){
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun InterstitialAd_show(){

        if (mInterstitialAd!!.isLoaded()) {
            mInterstitialAd?.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}