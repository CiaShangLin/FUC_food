package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.R

class BreakfastMenu : Menu() {

    override var errorDrawable: Int = R.drawable.ic_breakfast

    override fun getQuery(shop_tag: String, shop_id: Int): Query {
        var query = FirebaseDatabase.getInstance()
            .getReference().child("$shop_tag/$shop_id/menu")
        return query
    }

    override fun getOption(shop_tag: String, shop_name: String, shop_id: Int): FirebaseRecyclerOptions<Menu> {
        var options = FirebaseRecyclerOptions.Builder<BreakfastMenu>()
            .setQuery(
                getQuery(shop_tag, shop_id),
                getSnapParser(shop_tag, shop_name, shop_id) as SnapshotParser<BreakfastMenu>
            ).build()
        return options as FirebaseRecyclerOptions<Menu>
    }

    override fun getSnapParser(shop_tag: String, shop_name: String, shop_id: Int): SnapshotParser<Menu> {
        var snapshotParser = SnapshotParser<Menu> {
            var breakfastMenu = it.getValue(BreakfastMenu::class.java)!!
            breakfastMenu.shop_tag = shop_tag
            breakfastMenu.shop_name = shop_name
            breakfastMenu.shop_id = shop_id
            breakfastMenu
        }
        return snapshotParser
    }

}