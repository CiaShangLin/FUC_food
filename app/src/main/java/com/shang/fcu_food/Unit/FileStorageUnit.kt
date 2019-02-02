package com.shang.fcu_food.Unit

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.cardview_detailshop.view.*
import java.io.File

class FileStorageUnit{

    companion object {

        var listener=object :RequestListener<Drawable>{
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
                if(isFirstResource){
                    var ref=FirebaseStorage.getInstance().getReferenceFromUrl(model.toString())
                    ref.name
                    ref.parent
                    ref.parent?.parent
                    ref.root

                }

                return false
            }


        }

        fun ImageFileExists(context: Context,shop_tag:String,shop_name:String,name:String):Boolean{
            var file= File(getPath(context,shop_tag,shop_name,name))

            return file.exists()
        }

        fun getPath(context: Context,shop_tag:String,shop_name:String,name:String):String{
            var path=context.filesDir.absolutePath
            return "$path\\$shop_tag\\$shop_name\\$name.jpg"
        }

        fun ImageLoader(context: Context,shop_tag:String,shop_name:String,name:String){

        }

        fun StorageImage(context: Context){
            var ref=FirebaseUnits.storage_getImageRef("","","").downloadUrl
                .addOnSuccessListener {

            }.addOnFailureListener {

            }
            var path=context.filesDir.absolutePath
        }

    }

}