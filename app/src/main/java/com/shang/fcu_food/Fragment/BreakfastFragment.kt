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
import com.shang.fcu_food.Data.BreakfastShop
import com.shang.fcu_food.R
import com.shang.fcu_food.ShopViewHolder
import kotlinx.android.synthetic.main.shoplayout.view.*

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_breakfast, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var breakfastShop=view.findViewById<RecyclerView>(R.id.breakfastRecyc)
        var query = FirebaseDatabase.getInstance().getReference().child("breakfast")

        var options = FirebaseRecyclerOptions
            .Builder<BreakfastShop>()
            .setQuery(query, BreakfastShop::class.java)
            .build()

        var adpter = object : FirebaseRecyclerAdapter<BreakfastShop, ShopViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.shoplayout, parent, false)
                return ShopViewHolder(view)
            }

            override fun onBindViewHolder(holder: ShopViewHolder, position: Int, model: BreakfastShop) {
                holder.itemView.breakfastName.text = model.name
                holder.itemView.breakfastStar.text = model.address


                Log.d("TAG", snapshots.getSnapshot(position).key + " " + model.name + " " + model.address)
            }
        }

        breakfastShop.layoutManager=GridLayoutManager(activity?.baseContext,2)
        breakfastShop.adapter=adpter
        adpter.startListening()
    }

}