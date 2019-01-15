package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_detailmenu.view.*

class DetailMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var menuNameTv = itemView.findViewById<TextView>(R.id.menuNameTv)
    var menuPriceTv = itemView.findViewById<TextView>(R.id.menuPriceTv)
    var menuStarTv = itemView.findViewById<TextView>(R.id.menuStarTv)
    var menuCommentTv = itemView.findViewById<TextView>(R.id.menuCommentTv)
    var menuCommentRecyc = itemView.findViewById<TextView>(R.id.menuCommentRecyc)
    var menuPictureImg = itemView.findViewById<TextView>(R.id.menuPictureImg)

    fun bind(){

    }
}