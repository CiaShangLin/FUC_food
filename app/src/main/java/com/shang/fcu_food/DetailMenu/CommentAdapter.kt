package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_comment.view.*

class CommentAdapter(var commentList: MutableList<UserComment>): RecyclerView.Adapter<CommentVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CommentVH {
        var view=LayoutInflater.from(p0.context).inflate(R.layout.cardview_comment,p0,false)
        return CommentVH(view)

    }

    override fun getItemCount(): Int = commentList.size+5

    override fun onBindViewHolder(p0: CommentVH, p1: Int) {
        p0.bind()

    }
}

class CommentVH(itemView: View):RecyclerView.ViewHolder(itemView){

    var commentNameTv=itemView.findViewById<TextView>(R.id.commentNameTv)
    var commentContentTv=itemView.findViewById<TextView>(R.id.commentContentTv)
    var commentStarTv=itemView.findViewById<TextView>(R.id.commentStarTv)
    var commentImg=itemView.findViewById<ImageView>(R.id.commentImg)
    fun bind(){
        itemView.commentNameTv.setText("素還真")
        itemView.commentContentTv.setText("真的好吃 真的好吃 真的好吃 真的好吃 真的好吃 真的好吃 真的好吃 ")
        itemView.commentStarTv.setText("4.0")
    }
}