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
import com.shang.fcu_food.Data.DinnerShop
import com.shang.fcu_food.Data.Shop
import com.shang.fcu_food.Main.SimpleShopAdapter
import com.shang.fcu_food.Main.SimpleShopVH
import com.shang.fcu_food.R

class DinnerFragment :Fragment() {


    companion object {
        var dinnerFragment: DinnerFragment? = null

        fun getInstance(): DinnerFragment {
            if (dinnerFragment == null) {
                dinnerFragment = DinnerFragment()
            }

            return dinnerFragment as DinnerFragment
        }
    }

    lateinit var adapter: FirebaseRecyclerAdapter<Shop, SimpleShopVH>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_dinner, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dinnerShop = view.findViewById<RecyclerView>(R.id.dinnerRecyc)
        var query = FirebaseDatabase.getInstance().getReference().child(DinnerShop.tag)
        var options = FirebaseRecyclerOptions
            .Builder<DinnerShop>()
            .setQuery(query, DinnerShop::class.java)
            .build()

        adapter= SimpleShopAdapter(options as FirebaseRecyclerOptions<Shop>, DinnerShop.tag)

        dinnerShop.layoutManager = GridLayoutManager(activity?.baseContext, 2) as RecyclerView.LayoutManager?
        dinnerShop.adapter = adapter
        Log.d("TAG",DinnerShop.tag)
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