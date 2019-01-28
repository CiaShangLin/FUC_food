package com.shang.fcu_food

import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.*

class DataBind {
    companion object {
        var allUser = mutableMapOf<String, User>()  //所有使用者的資料

        var allBreakfastShop= mutableListOf<BreakfastShop>()
        var allDinnerShop= mutableListOf<DinnerShop>()
        var allSanckShop= mutableListOf<SnackShop>()
        var allDrinkShop= mutableListOf<DrinkShop>()
    }
}