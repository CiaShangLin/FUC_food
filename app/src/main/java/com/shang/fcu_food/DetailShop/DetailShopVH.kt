package com.shang.fcu_food.DetailShop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.DetailMenu.DetailMenuActivity
import com.shang.fcu_food.Data.DetailPlace
import com.shang.fcu_food.Dialog.ImageViewDialog
import com.shang.fcu_food.Dialog.LoadingDialog
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FileStorageUnit
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.cardview_detailshop.view.*
import org.jetbrains.anko.toast

class DetailShopVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var shopNameTv = itemView.findViewById<TextView>(R.id.shopNameTv)
    var shopPhoneTv = itemView.findViewById<TextView>(R.id.shopPhoneTv)
    var shopOpenTv = itemView.findViewById<TextView>(R.id.shopOpenTv)
    var shopStarTv = itemView.findViewById<TextView>(R.id.shopStarTv)
    var shopMenuRecyc = itemView.findViewById<RecyclerView>(R.id.shopMenuRecyc)
    var shopPictureImg = itemView.findViewById<ImageView>(R.id.shopPictureImg)
    var shopCommentSw = itemView.findViewById<SwitchCompat>(R.id.shopCommentSw)

    fun bind(model: Shop, position: Int, activity: DetailShopActivity) {

        itemView.shopNameTv.text = model.name
        itemView.shopOpenTv.text = model.time
        itemView.shopStarTv.text = String.format("%.1f", model.star)
        itemView.shopPhoneTv.text = model.phone

        //店家圖片
        itemView.shopPictureImg.setOnClickListener {
            ImageViewDialog.getInstance(model.shop_tag, model.name, model.name)
                .show(activity.supportFragmentManager, ImageViewDialog.TAG)
        }

        //店家圖片讀取
        FileStorageUnit.ImageLoader(
            itemView.context,
            itemView.shopPictureImg,
            model.getFile(itemView.context),
            model.getStorageRef(),
            model.errorDrawable,
            RequestOptions()
        )

        showMenuAdapter(model)

        itemView.shopCommentSw.setOnClickListener {
            if (itemView.shopCommentSw.isChecked) {
                itemView.shopCommentSw.setText("Google")
                GooglePlace.getInstance().getGooglePlaceData(model.place_id, object : Observer<DetailPlace> {
                    override fun onComplete() {
                        LoadingDialog.getInstance().dismiss()
                    }

                    override fun onSubscribe(d: Disposable) {
                        LoadingDialog.getInstance().show(activity.supportFragmentManager, LoadingDialog.TAG)
                    }

                    override fun onNext(t: DetailPlace) {
                        itemView.shopMenuRecyc.adapter = DetailPlaceAdapter(t)
                        itemView.shopMenuRecyc.layoutManager = LinearLayoutManager(itemView.context)
                    }

                    override fun onError(e: Throwable) {
                        itemView.context.toast("Google地圖沒有這家店")
                        LoadingDialog.getInstance().dismiss()
                    }
                })
            } else {
                itemView.shopCommentSw.setText("Food甲")
                showMenuAdapter(model)
            }
        }


    }

    //下面的RecycMenu
    fun showMenuAdapter(model: Shop) {
        itemView.shopMenuRecyc.layoutManager = GridLayoutManager(itemView.context, 2)
        itemView.shopMenuRecyc.adapter = SimpleMenuAdapter(model.menu, object : Consumer<Int> {
            override fun accept(position: Int) {
                var bundle = Bundle().apply {
                    putString(DataConstant.SHOP_NAME, model.name)
                    putString(DataConstant.SHOP_TYPE_TAG, model.shop_tag)
                    putInt(DataConstant.SHOP_ID, model.id)
                    putInt(DataConstant.POSITION, position)
                }
                var intent = Intent(itemView.context, DetailMenuActivity::class.java).apply { this.putExtras(bundle) }
                itemView.context.startActivity(intent)
            }
        })
    }

}