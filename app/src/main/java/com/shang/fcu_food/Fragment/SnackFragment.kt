package com.shang.fcu_food.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.Data.SnackShop
import com.shang.fcu_food.Main.SimpleShopAdapter
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class SnackFragment: Fragment() {

    companion object {
        var snackFragment: SnackFragment? = null

        fun getInstance(): SnackFragment {
            if (snackFragment == null) {
                snackFragment = SnackFragment()
            }

            return snackFragment as SnackFragment
        }
    }


    lateinit var adapter: FirebaseRecyclerAdapter<Shop, SimpleShopVH>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_snack, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var snackShop = view.findViewById<RecyclerView>(R.id.snackRecyc)
        var query = FirebaseDatabase.getInstance().getReference().child(SnackShop.tag)
        var options = FirebaseRecyclerOptions
            .Builder<SnackShop>()
            .setQuery(query, SnackShop::class.java)
            .build()

        adapter= SimpleShopAdapter(options as FirebaseRecyclerOptions<Shop>, SnackShop.tag)

        snackShop.layoutManager = GridLayoutManager(activity?.baseContext, 2) as RecyclerView.LayoutManager?
        snackShop.adapter = adapter

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