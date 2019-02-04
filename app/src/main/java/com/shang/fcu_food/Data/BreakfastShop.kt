package com.shang.fcu_food.Data

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.R

class BreakfastShop : Shop() {
    companion object {
        val tag:String="breakfast"
    }

    override var errorDrawable:Int= R.drawable.ic_shop

    override fun getQuery(): DatabaseReference? {
        return FirebaseDatabase.getInstance().getReference().child(tag)
    }

    override fun getOption(): FirebaseRecyclerOptions<Shop> {
        var options=FirebaseRecyclerOptions.Builder<BreakfastShop>()
            .setQuery(getQuery()!!,BreakfastShop::class.java).build()
        return options as FirebaseRecyclerOptions<Shop>
    }
}