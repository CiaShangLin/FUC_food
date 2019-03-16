package com.shang.fcu_food

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.shang.fcu_food.Main.GlideApp

class DetailPlaceAdapter(var detailPlace: DetailPlace) : RecyclerView.Adapter<DetailPlaceAdapter.DetailPlaceVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailPlaceVH {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_detailplace, p0, false)
        return DetailPlaceVH(view)
    }

    override fun getItemCount(): Int {
        return detailPlace.result.reviews.size
    }

    override fun onBindViewHolder(holder: DetailPlaceVH, position: Int) {
        if (detailPlace != null) {
            val reviews = detailPlace.result.reviews.get(position)
            holder.detailPlaceCommentTv.text = reviews.text
            holder.detailPlaceNameTv.text = reviews.author_name
            holder.detailPlaceTimeTv.text = reviews.relative_time_description
            holder.detailPlaceRatingBar.rating = reviews.rating.toFloat()
            GlideApp.with(holder.itemView.context)
                .load(reviews.profile_photo_url)
                .into(holder.detailPlacePictureImg)
        }


    }

    inner class DetailPlaceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var detailPlaceNameTv = itemView.findViewById<TextView>(R.id.detailPlaceNameTv)
        var detailPlacePictureImg = itemView.findViewById<ImageView>(R.id.detailPlacePictureImg)
        var detailPlaceRatingBar = itemView.findViewById<RatingBar>(R.id.detailPlaceRatingBar)
        var detailPlaceTimeTv = itemView.findViewById<TextView>(R.id.detailPlaceTimeTv)
        var detailPlaceCommentTv = itemView.findViewById<TextView>(R.id.detailPlaceCommentTv)

    }
}


