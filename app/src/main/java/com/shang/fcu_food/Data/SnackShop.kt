package com.shang.fcu_food.Data

import com.shang.fcu_food.R

class SnackShop : Shop() {
    companion object {
        val tag:String="snack"
    }
    override var errorDrawable:Int= R.drawable.ic_shop

}