package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.Query

interface FirebaseMenu {

    open fun getQuery(shop_tag: String, shop_id: Int): Query?

    open fun getOption(shop_tag: String, shop_name: String, shop_id: Int): FirebaseRecyclerOptions<Menu>?

    open fun getSnapParser(shop_tag: String, shop_name: String, shop_id: Int): SnapshotParser<Menu>?
}