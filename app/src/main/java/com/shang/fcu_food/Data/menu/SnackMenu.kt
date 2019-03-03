package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.R

class SnackMenu : Menu() {


    override var errorDrawable: Int = R.drawable.ic_snack

    override fun getQuery(shop_tag: String, shop_id: Int): Query {
        var query = FirebaseDatabase.getInstance()
            .getReference().child("$shop_tag/$shop_id/menu")
        return query
    }

    override fun getOption(shop_tag: String, shop_name: String, shop_id: Int): FirebaseRecyclerOptions<Menu> {
        var options = FirebaseRecyclerOptions.Builder<SnackMenu>()
            .setQuery(
                getQuery(shop_tag, shop_id),
                getSnapParser(shop_tag, shop_name, shop_id) as SnapshotParser<SnackMenu>
            ).build()
        return options as FirebaseRecyclerOptions<Menu>
    }

    override fun getSnapParser(shop_tag: String, shop_name: String, shop_id: Int): SnapshotParser<Menu> {
        var snapshotParser = SnapshotParser<Menu> {
            var snackMenu = it.getValue(SnackMenu::class.java)!!
            snackMenu.shop_tag = shop_tag
            snackMenu.shop_name = shop_name
            snackMenu.shop_id = shop_id
            snackMenu
        }
        return snapshotParser
    }
}