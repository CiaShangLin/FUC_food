package com.shang.fcu_food

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class DetailPlaceAdapter : RecyclerView.Adapter<DetailPlaceAdapter.DetailPlaceVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailPlaceVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_detailplace, p0, false)
        return DetailPlaceVH(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(p0: DetailPlaceVH, p1: Int) {

    }

    inner class DetailPlaceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var detailPlaceNameTv=itemView.findViewById<TextView>(R.id.detailPlaceNameTv)
        var detailPlacePictureImg=itemView.findViewById<ImageView>(R.id.detailPlacePictureImg)
        var detailPlaceRatingBar=itemView.findViewById<RatingBar>(R.id.detailPlaceRatingBar)
        var detailPlaceTimeTv=itemView.findViewById<TextView>(R.id.detailPlaceTimeTv)
        var detailPlaceCommentTv=itemView.findViewById<TextView>(R.id.detailPlaceCommentTv)

    }
}


