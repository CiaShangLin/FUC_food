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
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.Data.shop.SnackShop
import com.shang.fcu_food.Main.SimpleShopAdapter
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class SnackFragment : Fragment() {

    companion object {
        var snackFragment: SnackFragment? = null

        fun getInstance(): SnackFragment {
            if (snackFragment == null) {
                snackFragment = SnackFragment()
            }

            return snackFragment as SnackFragment
        }
    }


    lateinit var adapter: SimpleShopAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_snack, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var snackShop = SnackShop()
        var snackRecyc = view.findViewById<RecyclerView>(R.id.snackRecyc)

        adapter = SimpleShopAdapter(snackShop.getOption())

        snackRecyc.layoutManager = GridLayoutManager(activity?.baseContext, 2) as RecyclerView.LayoutManager?
        snackRecyc.adapter = adapter
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