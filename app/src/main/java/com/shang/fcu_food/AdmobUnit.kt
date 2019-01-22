package com.shang.fcu_food

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.cardview_detailshop.view.*

class AdmobUnit {
    companion object {
        var admobUnit:AdmobUnit?=null

        fun getInstance(context:Context): AdmobUnit? {
            if(admobUnit==null){
                MobileAds.initialize(context,"ca-app-pub-3596318314144695~8650129956")
                admobUnit=AdmobUnit()
            }
            return admobUnit
        }
    }

    fun show(adView:AdView){
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}