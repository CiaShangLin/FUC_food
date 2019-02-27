package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.BreakfastMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Data.menu.SnackMenu
import com.shang.fcu_food.R

class BreakfastShop : Shop() {

    companion object {
        var allBreakfastShop: MutableList<BreakfastShop> = mutableListOf<BreakfastShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.BREAKFAST_SHOP
    override var menu: MutableList<Menu> = mutableListOf<BreakfastMenu>().toMutableList()


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
            .setQuery(getQuery()!!, getSnapParser() as SnapshotParser<BreakfastShop>).build()
        return options as FirebaseRecyclerOptions<Shop>
    }

    override fun getSnapParser(): SnapshotParser<Shop> {
        var snapshotParser = SnapshotParser<Shop> {
            var breakfastShop = it.getValue(BreakfastShop::class.java)
            var breakfastMenu = mutableListOf<BreakfastMenu>()
            it.child("menu").children.forEach {
                breakfastMenu.add(it.getValue(BreakfastMenu::class.java)!!)
            }
            breakfastShop?.menu = breakfastMenu.toMutableList()
            breakfastShop!!
        }
        return snapshotParser
    }
}