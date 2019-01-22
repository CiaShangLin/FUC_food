package com.shang.fcu_food.DetailMenu

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_comment.view.*

class CommentAdapter(var commentList: MutableList<UserComment>) : RecyclerView.Adapter<CommentVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CommentVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_comment, p0, false)
        return CommentVH(view)
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(itemView: CommentVH, position: Int) {
        itemView.bind(position, commentList.get(position))
    }
}

class CommentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var commentNameTv = itemView.findViewById<TextView>(R.id.commentNameTv)
    var commentContentTv = itemView.findViewById<TextView>(R.id.commentContentTv)
    var commentStarTv = itemView.findViewById<TextView>(R.id.commentStarTv)
    var commentImg = itemView.findViewById<ImageView>(R.id.commentImg)
    var commentCardView = itemView.findViewById<CardView>(R.id.commentCV)


    fun bind(position: Int, model: UserComment) {
        itemView.commentNameTv.setText(getName(model.uid))
        itemView.commentContentTv.setText(model.comment)
        itemView.commentStarTv.setText(model.star.toString())

        commentCardView.setOnClickListener {
            if (commentContentTv.maxLines != 1) {
                it.layoutParams.height =
                        itemView.context.resources.getDimension(R.dimen.cardview_comment_height).toInt()
                commentContentTv.maxLines = 1
            } else {
                it.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                commentContentTv.maxLines = 20
            }
        }

        itemView.commentImg.setImageResource(getPicture(model.uid))
    }

    fun getName(uid: String): String {
        var name = DataBind.allUser.get(uid)?.name
        if (name != null)
            return name
        return "小明"
    }

    fun getPicture(uid: String): Int {
        var user = DataBind.allUser.get(uid)
        when (user?.picture) {
            "1" -> return R.drawable.ic_cat
            "2" -> return R.drawable.ic_dog
        }
        return R.drawable.ic_user
    }
}