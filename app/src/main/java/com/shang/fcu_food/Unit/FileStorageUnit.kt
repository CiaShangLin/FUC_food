package com.shang.fcu_food.Unit

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.widget.CircularProgressDrawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import java.io.File

class FileStorageUnit {

    companion object {
        private val TAG = "FileStorageUnit"

        //檔案路徑
        private fun getPath(context: Context, shop_tag: String, shop_name: String, name: String): String {
            var pathDir = File(context.filesDir.absolutePath + "/image")
            if (!pathDir.exists())
                pathDir.mkdirs()

            var tagDir = File(pathDir.path + "/$shop_tag")
            if (!tagDir.exists())
                tagDir.mkdirs()

            var shopDir = File(tagDir.path + "/$shop_name")
            if (!shopDir.exists())
                shopDir.mkdirs()

            return "${pathDir.absolutePath}/$shop_tag/$shop_name/$name.jpg"
        }

        fun ImageLoader(  //讀取圖片
            context: Context, shop_tag: String, shop_name: String, name: String,
            target: ImageView,
            errorDrawable: Int,
            options: RequestOptions
        ) {
            var file_path = getPath(context, shop_tag, shop_name, name)
            var file = File(file_path)
            var imageRef = FirebaseUnits.storage_getImageRef(shop_tag, shop_name, name)
            Log.d(TAG, "filePath:$file_path")
            Log.d(TAG, "fileExists:${file.exists()}")

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
