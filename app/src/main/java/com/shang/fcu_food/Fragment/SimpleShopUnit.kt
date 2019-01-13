package com.shang.fcu_food.Fragment

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.DetailShop.DetailShopVH
import com.shang.fcu_food.Main.SimpleShopVH

class SimpleShopUnit{

    companion object {
        fun getQuery(){

        }
        fun getRef(){

        }
        fun getOptions():FirebaseRecyclerOptions<Shop>{

            return FirebaseRecyclerOptions.Builder<BreakfastShop>().build()
        }
        fun getAdatper(){
            var adapter= object : FirebaseRecyclerAdapter<Shop,SimpleShopVH>(getOptions()){
                override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleShopVH {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onBindViewHolder(holder: SimpleShopVH, position: Int, model: Shop) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }

        }
    }
}