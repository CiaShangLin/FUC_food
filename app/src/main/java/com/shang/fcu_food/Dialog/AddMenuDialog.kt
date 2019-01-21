package com.shang.fcu_food.Dialog

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.TempMenu
import com.shang.fcu_food.FirebaseCallback
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.PickPictureUnit
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.dialog_addmenu.*
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.lang.Exception

class AddMenuDialog : DialogFragment() {

    companion object {
        val TAG = "AddMenuDialog"

        var addMenuDialog: AddMenuDialog? = null
        fun getInstance(shopName: String): AddMenuDialog {
            var bundle = Bundle().apply {
                this.putString(DataConstant.SHOP_NAME, shopName)
            }
            if (addMenuDialog == null) {
                addMenuDialog = AddMenuDialog()
            }
            addMenuDialog?.arguments = bundle
            return addMenuDialog!!
        }
    }

    var bitmap: Bitmap? = null
    lateinit var progressDialog: ProgressDialog

    var callback = object : FirebaseCallback {
        override fun statusCallBack(database_status: Boolean, storage_status: Boolean) {
            if (database_status && storage_status) {
                toast("新增成功")
                progressDialog.dismiss()
                dismiss()
            } else if (database_status == false && storage_status == false) {
                progressDialog.dismiss()
                toast("新增失敗")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_addmenu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var shop_name = arguments?.getString(DataConstant.SHOP_NAME)

        var addMenuNameTvEt = view.findViewById<TextInputLayout>(R.id.addMenuNameTvEt)
        var addMenuPriceTvEt = view.findViewById<TextInputLayout>(R.id.addMenuPriceTvEt)
        var addMenuCommentTvEt = view.findViewById<TextInputLayout>(R.id.addMenuCommentTvEt)
        var addMenuRatingBar = view.findViewById<RatingBar>(R.id.addMenuRatingBar)
        var addMenuPictureIg = view.findViewById<ImageView>(R.id.addMenuPictureIg)
        var addMenuAddBt = view.findViewById<Button>(R.id.addMenuAddBt)

        progressDialog=ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }

        addMenuAddBt.setOnClickListener {
            try {
                progressDialog.show()
                var ref = "tempMenu"
                var menu_name = addMenuNameTvEt.editText?.text.toString()
                var star = addMenuRatingBar.rating.toDouble()
                var price = addMenuPriceTvEt.editText?.text.toString().toInt()
                var uid = FirebaseUnits.auth_getUser()?.uid
                var comment = addMenuCommentTvEt.editText?.text.toString()
                var tempMenu = TempMenu(shop_name!!, menu_name, star, price, uid!!, comment)

                FirebaseUnits.add(ref, tempMenu, bitmapTobyte(bitmap), callback)
            } catch (e: Exception) {
                toast("輸入錯誤")
            }
        }

        addMenuPictureIg.setOnClickListener {
            startActivityForResult(PickPictureUnit.pickIntent(), PickPictureUnit.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PickPictureUnit.REQUEST_CODE) {

            bitmap = PickPictureUnit.uriToBitmap(activity!!,data)
            GlideApp.with(context!!)
                .load(data?.data)
                .into(addMenuPictureIg)
        }
    }

    fun bitmapTobyte(bitmap: Bitmap?): ByteArray {
        var byteArrayOutputStream = ByteArrayOutputStream()
        if (bitmap == null) {
            return byteArrayOf()
        } else {
            //有可能是長得或是橫的
            var width = if (bitmap.width > bitmap.height) 720 else 480
            var height = if (bitmap.height > bitmap.width) 720 else 480
            var tempBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        }
        return byteArrayOutputStream.toByteArray()
    }

    override fun onResume() {
        super.onResume()

        var dialog = dialog
        if (dialog != null) {
            var width = ViewGroup.LayoutParams.MATCH_PARENT
            var height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }


}