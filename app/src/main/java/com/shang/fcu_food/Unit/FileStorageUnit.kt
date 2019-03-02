package com.shang.fcu_food.Unit

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Data.shop.Shop
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_maps.view.*
import kotlinx.android.synthetic.main.cardview_detailshop.view.*
import java.io.File

class FileStorageUnit {

    companion object {
        private val TAG = "FileStorageUnit"

        fun createFile(context: Context, shop_tag: String, shop_name: String, name: String): File {
            var pathDir = File(context.filesDir.absolutePath + "/image")
            if (!pathDir.exists())
                pathDir.mkdirs()

            var tagDir = File(pathDir.path + "/$shop_tag")
            if (!tagDir.exists())
                tagDir.mkdirs()

            var shopDir = File(tagDir.path + "/$shop_name")
            if (!shopDir.exists())
                shopDir.mkdirs()
            return File("${pathDir.absolutePath}/$shop_tag/$shop_name/$name.jpg")
        }

        //讀取圖片
        fun ImageLoader(
            context: Context,
            target: ImageView,
            file: File,
            imageRef: StorageReference,
            errorDrawable: Int,
            options: RequestOptions
        ) {
            GlideApp.with(context)
                .load(if (file.exists()) file else imageRef)
                .error(errorDrawable)
                .addListener(FileRequestListener(file))
                .placeholder(getLoadingDrawable(context))
                .apply(options)
                .into(target)
        }


        private fun getLoadingDrawable(context: Context): CircularProgressDrawable {
            var circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 60f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }

    }

}
