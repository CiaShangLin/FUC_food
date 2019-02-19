package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.shang.fcu_food.R

class DrinkMenu: Menu() {
    override var errorDrawable:Int= R.drawable.ic_drink

    override fun getQuery(shop_tag: String, shop_id: String): Query {
        var query=FirebaseDatabase.getInstance()
            .getReference().child("$shop_tag/$shop_id/menu")
        return query
    }

    override fun getOption(shop_tag: String, shop_id: String): FirebaseRecyclerOptions<Menu> {
        var options = FirebaseRecyclerOptions.Builder<DrinkMenu>()
            .setQuery(getQuery(shop_tag,shop_id), DrinkMenu::class.java).build()
        return options as FirebaseRecyclerOptions<Menu>
    }
}