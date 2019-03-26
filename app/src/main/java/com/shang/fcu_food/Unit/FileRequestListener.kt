package com.shang.fcu_food.Unit

import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class FileRequestListener(var file: File) : RequestListener<Drawable> {
    val TAG = "FileRequestListener"

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        if (isFirstResource) {
            if (!file.exists()) {    //沒有檔案的話
                file.createNewFile()
                (model as StorageReference).getFile(file).addOnSuccessListener {
                    Log.d(TAG, "成功下載:" + it.storage.name)
                }.addOnFailureListener {
                    Log.d(TAG, "下載失敗:" + it.message)
                }
            }
        }

        return false
    }
}