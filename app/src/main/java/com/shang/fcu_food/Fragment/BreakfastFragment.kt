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
import com.shang.fcu_food.Data.shop.BreakfastShop
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.Main.SimpleShopAdapter
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class BreakfastFragment : Fragment() {

    companion object {
        var breakfastFragment: BreakfastFragment? = null

        fun getInstance(): BreakfastFragment {
            if (breakfastFragment == null) {
                breakfastFragment = BreakfastFragment()
            }
            return breakfastFragment as BreakfastFragment
        }
    }

    lateinit var adapter: SimpleShopAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_breakfast, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var breakfastRecyc = view.findViewById<RecyclerView>(R.id.breakfastRecyc)

        var breakfastShop = BreakfastShop()

        adapter = SimpleShopAdapter(breakfastShop.getOption())

        breakfastRecyc.layoutManager = GridLayoutManager(activity?.baseContext, 2)
        breakfastRecyc.adapter = adapter

        //Log.d("TAG", snapshots.getSnapshot(position).key + " " + model.name + " " + model.address)
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