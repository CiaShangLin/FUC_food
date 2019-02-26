package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.DrinkMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Data.menu.SnackMenu
import com.shang.fcu_food.R

class SnackShop : Shop() {

    companion object {
        var allSnackShop: MutableList<SnackShop> = mutableListOf<SnackShop>()
    }

    override var errorDrawable: Int = R.drawable.ic_shop
    override var shop_tag: String = Shop.SNACK_SHOP
    override var menu: MutableList<Menu> = mutableListOf<SnackMenu>().toMutableList()

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
            .setQuery(getQuery()!!, getSnapParser() as SnapshotParser<SnackShop>).build()
        return options as FirebaseRecyclerOptions<Shop>
    }

    override fun getSnapParser(): SnapshotParser<Shop> {
        var snapshotParser = SnapshotParser<Shop> {
            var snackShop = it.getValue(SnackShop::class.java)
            var snackMenu = mutableListOf<SnackMenu>()
            it.child("menu").children.forEach {
                snackMenu.add(it.getValue(SnackMenu::class.java)!!)
            }
            snackShop?.menu = snackMenu.toMutableList()
            snackShop!!
        }
        return snapshotParser
    }

}