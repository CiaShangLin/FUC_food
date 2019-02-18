package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.BreakfastMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.R

class BreakfastShop : Shop() {

    companion object {
        //val tag: String = "breakfast"
        var allBreakfastShop: MutableList<BreakfastShop> = mutableListOf<BreakfastShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.BREAKFAST_SHOP

    override fun getQuery(): Query {
        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                allBreakfastShop.clear()
                for (data in snapshot.children) {
                    var breakfastShop = data.getValue(BreakfastShop::class.java)
                    allBreakfastShop.add(breakfastShop!!)
                }
            }
        })
        return query
    }

    override fun getOption(): FirebaseRecyclerOptions<Shop> {
        var options = FirebaseRecyclerOptions.Builder<BreakfastShop>()
            .setQuery(getQuery()!!, BreakfastShop::class.java).build()
        return options as FirebaseRecyclerOptions<Shop>
    }
}