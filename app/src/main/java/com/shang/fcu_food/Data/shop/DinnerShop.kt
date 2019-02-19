package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.menu.BreakfastMenu
import com.shang.fcu_food.Data.menu.DinnerMenu
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.R

class DinnerShop : Shop(){
    companion object {
        //val tag:String="dinner"
        var allDinnerShop: MutableList<DinnerShop> = mutableListOf<DinnerShop>()
    }

    override var errorDrawable:Int= R.drawable.ic_shop
    override var shop_tag: String = Shop.DINNER_SHOP


    override fun getQuery(): Query {
        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                allDinnerShop.clear()
                for(data in snapshot.children){
                    var dinnerShop=data.getValue(DinnerShop::class.java)
                    allDinnerShop.add(dinnerShop!!)
                }
            }
        })
        return query
    }

    override fun getOption(): FirebaseRecyclerOptions<Shop> {
        var options = FirebaseRecyclerOptions.Builder<DinnerShop>()
            .setQuery(getQuery()!!, DinnerShop::class.java).build()
        return options as FirebaseRecyclerOptions<Shop>
    }
}