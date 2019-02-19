package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.DrinkMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.R

class DrinkShop : Shop() {
    companion object {
        //val tag: String = "drink"
        var allDrinkShop: MutableList<DrinkShop> = mutableListOf<DrinkShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.DRINK_SHOP


    override fun getQuery(): Query {
        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                allDrinkShop.clear()
                for (data in snapshot.children) {
                    var drinkShop = data.getValue(DrinkShop::class.java)
                    allDrinkShop.add(drinkShop!!)
                }
            }
        })
        return query
    }

    override fun getOption(): FirebaseRecyclerOptions<Shop> {
        var options = FirebaseRecyclerOptions.Builder<DrinkShop>()
            .setQuery(getQuery()!!, DrinkShop::class.java).build()
        return options as FirebaseRecyclerOptions<Shop>
    }
}