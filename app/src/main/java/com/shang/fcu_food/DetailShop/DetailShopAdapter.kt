package com.shang.fcu_food.DetailShop

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R

class DetailShopAdapter(
    var shop_tag: String, var activity: DetailShopActivity,
    options: FirebaseRecyclerOptions<Any>
) : FirebaseRecyclerAdapter<Any, DetailShopVH>(options) {

    companion object {
        private fun getQuery(shop_tag: String): DatabaseReference {
            var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
            query.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    var allShop = mutableListOf<Shop>()
                    for (data in snapshot.children) {
                        var shop = data.getValue(Shop::class.java)
                        allShop.add(shop!!)
                    }
                    when (shop_tag) {
                        BreakfastShop.tag -> DataBind.allBreakfastShop = allShop as MutableList<BreakfastShop>
                        DinnerShop.tag -> DataBind.allDinnerShop = allShop as MutableList<DinnerShop>
                        SnackShop.tag -> DataBind.allSanckShop = allShop as MutableList<SnackShop>
                        DrinkShop.tag -> DataBind.allDrinkShop = allShop as MutableList<DrinkShop>
                    }
                }
            })
            return query
        }

        internal fun getOptions(shop_tag: String): FirebaseRecyclerOptions<Any> {
            var query = getQuery(shop_tag)
            var options: Any? = null
            when (shop_tag) {
                BreakfastShop.tag ->
                    options = FirebaseRecyclerOptions.Builder<BreakfastShop>()
                        .setQuery(query, BreakfastShop::class.java).build()
                DinnerShop.tag ->
                    options = FirebaseRecyclerOptions.Builder<DinnerShop>()
                        .setQuery(query, DinnerShop::class.java).build()
                SnackShop.tag ->
                    options = FirebaseRecyclerOptions.Builder<SnackShop>()
                        .setQuery(query, SnackShop::class.java).build()
                DrinkShop.tag ->
                    options = FirebaseRecyclerOptions.Builder<DrinkShop>()
                        .setQuery(query, DrinkShop::class.java).build()
            }
            return options as FirebaseRecyclerOptions<Any>
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailShopVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_detailshop, parent, false)
        return DetailShopVH(view)
    }

    override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Any) {
        holder.bind(shop_tag, model as Shop, position, activity)
    }


    fun recommend(detailShopRecyc: RecyclerView, position: Int) {
        detailShopRecyc.smoothScrollToPosition(0)
    }


}