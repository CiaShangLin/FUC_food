package com.shang.fcu_food.Unit

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Main.GlideApp
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
            Log.d(TAG, "filePath:$file_path")
            Log.d(TAG, "fileExists:${file.exists()}")

            if (file.exists()) {  //有檔案直接讀檔
                GlideApp.with(context).load(file).error(errorDrawable).apply(options).into(target)
            } else {
                GlideApp.with(context)
                    .load(FirebaseUnits.storage_getImageRef(shop_tag, shop_name, name))
                    .addListener(FileRequestListener(file))
                    .error(errorDrawable)
                    .apply(options)
                    .into(target)
            }
        }

    }

}
