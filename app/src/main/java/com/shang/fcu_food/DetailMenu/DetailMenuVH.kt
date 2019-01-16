package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.Menu
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_detailmenu.view.*

class DetailMenuVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var menuNameTv = itemView.findViewById<TextView>(R.id.menuNameTv)
    var menuPriceTv = itemView.findViewById<TextView>(R.id.menuPriceTv)
    var menuStarTv = itemView.findViewById<TextView>(R.id.menuStarTv)
    var menuCommentTv = itemView.findViewById<TextView>(R.id.menuCommentTv)
    var menuCommentRecyc = itemView.findViewById<RecyclerView>(R.id.menuCommentRecyc)
    var menuPictureImg = itemView.findViewById<ImageView>(R.id.menuPictureImg)

    fun bind(position: Int, model: Menu){
        itemView.menuNameTv.setText(model.name)
        itemView.menuPriceTv.setText("${model.price}元")
        itemView.menuStarTv.setText("${model.star}")
        itemView.menuCommentTv.setText("${model.usercomment.size}人")

        itemView.menuCommentRecyc.layoutManager=LinearLayoutManager(itemView.context)
        itemView.menuCommentRecyc.adapter=CommentAdapter(mutableListOf())

    }
}