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
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.Data.Shop
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

    lateinit var adapter:FirebaseRecyclerAdapter<Shop, SimpleShopVH>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_breakfast, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var breakfastShop = view.findViewById<RecyclerView>(R.id.breakfastRecyc)
        var query = FirebaseDatabase.getInstance().getReference().child(BreakfastShop.tag)
        var options = FirebaseRecyclerOptions
            .Builder<BreakfastShop>()
            .setQuery(query, BreakfastShop::class.java)
            .build()

        adapter=SimpleShopAdapter(options as FirebaseRecyclerOptions<Shop>,BreakfastShop.tag)

        breakfastShop.layoutManager = GridLayoutManager(activity?.baseContext, 2) as RecyclerView.LayoutManager?
        breakfastShop.adapter = adapter

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