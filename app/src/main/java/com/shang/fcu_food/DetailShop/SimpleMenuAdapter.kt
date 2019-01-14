package com.shang.fcu_food.DetailShop

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.Menu
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_simplemenu.view.*

class SimpleMenuAdapter(var menuList:MutableList<Menu>) : RecyclerView.Adapter<SimpleMenuVH>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleMenuVH {
        var view=LayoutInflater.from(p0.context).inflate(R.layout.cardview_simplemenu,p0,false)
        return SimpleMenuVH(view)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: SimpleMenuVH, position: Int) {
        holder.bind(holder,position,menuList.get(position))

    }
}

class SimpleMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var simpleMenuName=itemView.findViewById<TextView>(R.id.simpleMenuName)
    var simpleMenuPrice=itemView.findViewById<TextView>(R.id.simpleMenuPrice)
    var simpleMenuStar=itemView.findViewById<TextView>(R.id.simpleMenuStar)
    var simpleMenuImg=itemView.findViewById<ImageView>(R.id.simpleMenuImg)

    fun bind(holder: SimpleMenuVH,position: Int,model:Menu){
        holder.itemView.simpleMenuName.setText(model.name)
        holder.itemView.simpleMenuPrice.setText(model.price.toString())
        holder.itemView.simpleMenuStar.setText("3.5")
        holder.itemView.setOnClickListener {
            
        }

    }
}