package com.shang.fcu_food.DetailShop

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FileStorageUnit
import kotlinx.android.synthetic.main.activity_maps.view.*
import kotlinx.android.synthetic.main.cardview_simplemenu.view.*

class SimpleMenuAdapter(
    var menuList: MutableList<Menu>,
    var onItemClick: OnItemClickHandler
) : RecyclerView.Adapter<SimpleMenuVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleMenuVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_simplemenu, p0, false)
        return SimpleMenuVH(view)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: SimpleMenuVH, position: Int) {
        holder.bind(position, menuList.get(position),onItemClick)
    }
}

class SimpleMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var simpleMenuName = itemView.findViewById<TextView>(R.id.simpleMenuName)
    var simpleMenuPrice = itemView.findViewById<TextView>(R.id.simpleMenuPrice)
    var simpleMenuStar = itemView.findViewById<TextView>(R.id.simpleMenuStar)
    var simpleMenuImg = itemView.findViewById<ImageView>(R.id.simpleMenuImg)

    fun bind(
        position: Int,
        model: Menu,
        onItemClick: OnItemClickHandler
    ) {
        itemView.simpleMenuName.text = model.name
        itemView.simpleMenuPrice.text = model.price.toString() + "元"
        itemView.simpleMenuStar.text = String.format("%.1f", model.star)
        itemView.setOnClickListener {
            var bundle = Bundle().apply {
                this.putInt(DataConstant.POSITION, position)
            }
            onItemClick.onItemClick(bundle)
        }

        //Menu圖片讀取
        FileStorageUnit.ImageLoader(
            itemView.context,
            itemView.simpleMenuImg,
            model.getFile(itemView.context),
            model.getStorageRef(),
            model.errorDrawable,
            RequestOptions().circleCrop()
        )

    }

}

