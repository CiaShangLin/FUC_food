package com.shang.fcu_food.Unit

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.shang.fcu_food.R

class AdmobUnit {
    companion object {
        private var admobUnit: AdmobUnit? = null
        private var mInterstitialAd: InterstitialAd? = null
        fun getInstance(context: Context): AdmobUnit? {
            if (admobUnit == null) {
                admobUnit = AdmobUnit()
                MobileAds.initialize(context, "ca-app-pub-3596318314144695~8650129956")
                mInterstitialAd = InterstitialAd(context)
                mInterstitialAd?.setAdUnitId(context.resources.getString(R.string.admob_interstitial))
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }
            return admobUnit
        }
    }

    //記得要去打開註解的程式碼 DetailMenuActivity , DetailShopActivity , MainActivity,AddCommentDialog
    //插頁 activity_detail_shop activity_detail_menu dialog_addcomment
    fun show(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun InterstitialAd_show() {
        if (mInterstitialAd!!.isLoaded()) {
            mInterstitialAd?.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}