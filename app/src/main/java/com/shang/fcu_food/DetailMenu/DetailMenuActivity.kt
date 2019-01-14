package com.shang.fcu_food.DetailMenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shang.fcu_food.R

class DetailMenuActivity : AppCompatActivity() {

    companion object {
        val POSITION="POSITION"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
    }
}
