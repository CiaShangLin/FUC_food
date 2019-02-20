package com.shang.fcu_food.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.shop.DrinkShop
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.Main.SimpleShopAdapter
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class DrinkFragment : Fragment() {

    companion object {
        private var drinkFragment: DrinkFragment? = null

        fun getInstance(): DrinkFragment {
            if (drinkFragment == null) {
                drinkFragment = DrinkFragment()
            }

            return drinkFragment as DrinkFragment
        }
    }


    lateinit var adapter: SimpleShopAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_drink, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var drinkShop = DrinkShop()
        var drinkRecyc = view.findViewById<RecyclerView>(R.id.drinkRecyc)

        adapter = SimpleShopAdapter(drinkShop.getOption())

        drinkRecyc.layoutManager = GridLayoutManager(activity?.baseContext, 2)
        drinkRecyc.adapter = adapter

    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}