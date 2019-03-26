package com.shang.fcu_food.Dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.widget.CircularProgressDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import org.jetbrains.anko.imageBitmap

//點擊圖片後放大用
class ImageViewDialog : DialogFragment() {

    companion object {
        val TAG = "ImageViewDialog"
        private val SHOP_TAG = "SHOP_TAG"
        private val SHOP_NAME = "SHOP_NAME"
        private val IMAGE_NAME = "IMAGE_NAME"

        private var imageViewDialog: DialogFragment? = null

        fun getInstance(shop_tag: String, shop_name: String, name: String): ImageViewDialog {
            if (imageViewDialog == null) {
                imageViewDialog = ImageViewDialog()
            }
            imageViewDialog?.arguments = Bundle().apply {
                this.putString(SHOP_TAG, shop_tag)
                this.putString(SHOP_NAME, shop_name)
                this.putString(IMAGE_NAME, name)
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

        var shop_tag = arguments?.getString(SHOP_TAG)
        var shop_name = arguments?.getString(SHOP_NAME)
        var image_name = arguments?.getString(IMAGE_NAME)

        var dialogImg = view.findViewById<ImageView>(R.id.dialog_image)
        var closeBt = view.findViewById<ImageButton>(R.id.closeImgBt)
        closeBt.setOnClickListener {
            dismiss()
        }

        GlideApp.with(context!!)
            .load(FirebaseFactory.getMyFirebaseStorage().storage_getImageRef(shop_tag!!, shop_name!!, image_name!!))
            .error(R.drawable.ic_error)
            .placeholder(getLoadingDrawable(context!!))
            .into(dialogImg)


    }

    private fun getLoadingDrawable(context: Context): CircularProgressDrawable {
        var circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 60f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }


}