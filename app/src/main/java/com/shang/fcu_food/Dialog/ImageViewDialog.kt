package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits

//點擊圖片後放大用
class ImageViewDialog : DialogFragment() {

    companion object {
        val TAG = "ImageViewDialog"
        private val NAME = "NAME"

        private var imageViewDialog: DialogFragment? = null

        fun getInstance(shop_tag: String, shop_name: String, name: String): ImageViewDialog {
            if (imageViewDialog == null) {
                imageViewDialog = ImageViewDialog()
            }
            imageViewDialog?.arguments = Bundle().apply {
                this.putString(DataConstant.SHOP_TYPE_TAG, shop_tag)
                this.putString(DataConstant.SHOP_NAME, shop_name)
                this.putString(NAME, name)
            }

            return imageViewDialog as ImageViewDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_imageview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var shop_tag = arguments?.getString(DataConstant.SHOP_TYPE_TAG)
        var shop_name = arguments?.getString(DataConstant.SHOP_NAME)
        var image_name = arguments?.getString(NAME)

        var dialogImg = view.findViewById<ImageView>(R.id.dialog_image)
        var closeBt = view.findViewById<ImageButton>(R.id.closeImgBt)
        closeBt.setOnClickListener {
            dismiss()
        }

        GlideApp.with(context!!)
            .load(FirebaseUnits.storage_getImageRef(shop_tag!!, shop_name!!, image_name!!))
            .error(R.drawable.ic_error)
            .into(dialogImg)
    }


}