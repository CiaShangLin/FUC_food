package com.shang.fcu_food.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_snack, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var breakfastShop=view.findViewById<RecyclerView>(R.id.breakfastRecyc)
        //adpter.startListening()
    }
}