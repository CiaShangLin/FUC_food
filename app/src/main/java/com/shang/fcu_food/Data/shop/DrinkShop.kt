package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.DrinkMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.R

class DrinkShop : Shop() {
    companion object {
        var allDrinkShop: MutableList<DrinkShop> = mutableListOf<DrinkShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.DRINK_SHOP
    override var menu: MutableList<Menu> = mutableListOf<DrinkMenu>().toMutableList()

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
            .setQuery(getQuery()!!, getSnapParser() as SnapshotParser<DrinkShop>).build()
        return options as FirebaseRecyclerOptions<Shop>
    }

    override fun getSnapParser(): SnapshotParser<Shop> {
        var snapshotParser = SnapshotParser<Shop> {
            var drinkShop = it.getValue(DrinkShop::class.java)!!
            var drinkMenu = mutableListOf<DrinkMenu>()
            it.child("menu").children.forEach {
                var menu = it.getValue(DrinkMenu::class.java)!!
                drinkMenu.add(menu)
            }
            drinkShop?.menu = drinkMenu.toMutableList()
            drinkShop!!
        }
        return snapshotParser
    }

}