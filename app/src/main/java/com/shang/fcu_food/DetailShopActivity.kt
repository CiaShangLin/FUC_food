package com.shang.fcu_food

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DetailShopActivity : AppCompatActivity() {

    companion object {
        val TAG = "TAG"     //傳遞店家TAG參數
        val POSITION = "POSITION"  //傳遞哪一個位置
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

    }
}
