package com.shang.fcu_food.DetailMenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R

class DetailMenuActivity : AppCompatActivity() {

    companion object {
        val POSITION="POSITION"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        Log.d("TAG",DataBind.menu.size.toString())
        Log.d("TAG",DataBind.menu.get(0).name+" "+DataBind.menu.get(0).price)
    }
}
