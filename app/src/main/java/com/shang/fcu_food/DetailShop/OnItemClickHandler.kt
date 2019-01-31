package com.shang.fcu_food.DetailShop

import android.os.Bundle

interface OnItemClickHandler  {
    //為了同時取得 simpleMenuAdapter的position和DetailShop的tag和id
    fun onItemClick(bundle: Bundle){}
}