package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.DrinkMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Data.menu.SnackMenu
import com.shang.fcu_food.R

class SnackShop : Shop() {
    companion object {
        //val tag: String = "snack"
        var allSnackShop: MutableList<SnackShop> = mutableListOf<SnackShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.SNACK_SHOP


    override fun getQuery(): Query {
        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                allSnackShop.clear()
                for (data in snapshot.children) {
                    var snackShop = data.getValue(SnackShop::class.java)
                    allSnackShop.add(snackShop!!)
                }
            }
        })
        return query
    }

    override fun getOption(): FirebaseRecyclerOptions<Shop> {
        var options = FirebaseRecyclerOptions.Builder<SnackShop>()
            .setQuery(getQuery()!!, SnackShop::class.java).build()
        return options as FirebaseRecyclerOptions<Shop>
    }

}