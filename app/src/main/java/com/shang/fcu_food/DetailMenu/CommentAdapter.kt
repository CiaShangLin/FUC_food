package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.R

class CommentAdapter(var commentList: MutableList<UserComment>): RecyclerView.Adapter<CommentVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CommentVH {
        var view=LayoutInflater.from(p0.context).inflate(R.layout.cardview_comment,p0,false)
        return CommentVH(view)

    }

    override fun getItemCount(): Int = commentList.size+5

    override fun onBindViewHolder(p0: CommentVH, p1: Int) {

    }
}

class CommentVH(itemView: View):RecyclerView.ViewHolder(itemView){

    fun bind(){

    }
}