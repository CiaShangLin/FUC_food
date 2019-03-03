package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.R

class DrinkMenu : Menu() {


    override var errorDrawable: Int = R.drawable.ic_drink

    override fun getQuery(shop_tag: String, shop_id: Int): Query {
        var query = FirebaseDatabase.getInstance()
            .getReference().child("$shop_tag/$shop_id/menu")
        return query
    }

    override fun getOption(shop_tag: String, shop_name: String, shop_id: Int): FirebaseRecyclerOptions<Menu>? {
        var options = FirebaseRecyclerOptions.Builder<DrinkMenu>()
            .setQuery(
                getQuery(shop_tag, shop_id),
                getSnapParser(shop_tag, shop_name, shop_id) as SnapshotParser<DrinkMenu>
            ).build()
        return options as FirebaseRecyclerOptions<Menu>
    }


    override fun getSnapParser(shop_tag: String, shop_name: String, shop_id: Int): SnapshotParser<Menu> {
        var snapshotParser = SnapshotParser<Menu> {
            var drinkMenu = it.getValue(DrinkMenu::class.java)!!
            drinkMenu.shop_tag = shop_tag
            drinkMenu.shop_name = shop_name
            drinkMenu.shop_id = shop_id
            drinkMenu
        }
        return snapshotParser
    }
}