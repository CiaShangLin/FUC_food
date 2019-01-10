package com.shang.fcu_food.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shang.fcu_food.R

class DrinkFragment: Fragment() {

    companion object {
        var drinkFragment: DrinkFragment? = null

        fun getInstance(): DrinkFragment {
            if (drinkFragment == null) {
                drinkFragment = DrinkFragment()
            }

            return drinkFragment as DrinkFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_drink, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var breakfastShop=view.findViewById<RecyclerView>(R.id.breakfastRecyc)
        //adpter.startListening()
    }
}