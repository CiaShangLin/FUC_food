package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query

interface FirebaseMenu {

    abstract open fun getQuery(shop_tag: String, shop_id: String): Query?

    abstract open fun getOption(shop_tag: String, shop_id: String): FirebaseRecyclerOptions<Menu>?
}