package com.shang.fcu_food.Fragment

import android.content.Intent
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
import com.shang.fcu_food.DetailShopActivity
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.R
import com.shang.fcu_food.Main.SimpleShopVH
import kotlinx.android.synthetic.main.cardview_simpleshop.view.*

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

        var breakfastShop = view.findViewById<RecyclerView>(R.id.breakfastRecyc)
        var query = FirebaseDatabase.getInstance().getReference().child("breakfast")

        var options = FirebaseRecyclerOptions
            .Builder<BreakfastShop>()
            .setQuery(query, BreakfastShop::class.java)
            .build()

        var adpter = object : FirebaseRecyclerAdapter<BreakfastShop, SimpleShopVH>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleShopVH {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_simpleshop, parent, false)
                return SimpleShopVH(view)
            }

            override fun onBindViewHolder(holder: SimpleShopVH, position: Int, model: BreakfastShop) {
                holder.itemView.breakfastName.text = model.name
                holder.itemView.breakfastStar.text = model.address

                FirebaseUnits.storage_loadImg(activity!!,holder.itemView.breakfastImg,model.tag,model.name)
                holder.itemView.setOnClickListener {
                    goDetailShop_Activity(model.tag,position)
                }
                Log.d("TAG", snapshots.getSnapshot(position).key + " " + model.name + " " + model.address)
            }
        }

        breakfastShop.layoutManager = GridLayoutManager(activity?.baseContext, 2) as RecyclerView.LayoutManager?
        breakfastShop.adapter = adpter
        adpter.startListening()
    }

    fun goDetailShop_Activity(tag:String,position:Int){
        var bundle=Bundle().apply {
            this.putString(DetailShopActivity.TAG,tag)
            this.putInt(DetailShopActivity.POSITION,position)
        }
        var intent= Intent(activity,DetailShopActivity::class.java).apply {
            this.putExtras(bundle)
        }
        activity?.startActivity(intent)
    }


}