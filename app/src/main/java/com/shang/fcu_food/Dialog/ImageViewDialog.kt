package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R

class ImageViewDialog : DialogFragment() {

    companion object {
        val TAG = "ImageViewDialog"
        val SHOP_NAME="SHOP_NAME"
        val SHOP_TAG="SHOP_TAG"
        val NAME="NAME"

        var imageViewDialog: DialogFragment? = null

        fun getInstance(shop_tag: String, shop_name: String, name: String): ImageViewDialog {
            var bundle=Bundle()
            bundle.putString(SHOP_NAME,shop_name)
            bundle.putString(SHOP_TAG,shop_tag)
            bundle.putString(NAME,name)

            if (imageViewDialog == null) {
                imageViewDialog =
                        ImageViewDialog()

            }
            imageViewDialog?.arguments=bundle
            return imageViewDialog as ImageViewDialog
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_imageview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var shop_tag=arguments?.getString(SHOP_TAG)
        var shop_name=arguments?.getString(SHOP_NAME)
        var name=arguments?.getString(NAME)
        Log.d("TAG",shop_name+" "+shop_tag+" "+name)

        var dialogImg=view.findViewById<ImageView>(R.id.dialog_image)
        var closeBt=view.findViewById<ImageButton>(R.id.closeImgBt)
        closeBt.setOnClickListener {
            dismiss()
        }

        var ref=FirebaseStorage.getInstance()
            .getReference(shop_tag!!).child(shop_name!!).child("$name.jpg")
        GlideApp.with(activity!!).load(ref).into(dialogImg)
    }

    override fun onResume() {
        super.onResume()

        var dialog=dialog
        if(dialog!=null){
            var width=ViewGroup.LayoutParams.MATCH_PARENT
            var height=ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width,height)
        }
    }

}