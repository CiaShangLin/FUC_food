package com.shang.fcu_food.Dialog

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.TempShop
import com.shang.fcu_food.FirebaseCallback
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.PickPictureUnit
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.dialog_addshop.*
import org.jetbrains.anko.support.v4.toast
import java.io.IOException
import java.lang.Exception

class AddShopDialog : DialogFragment() {

    companion object {
        val TAG = "AddShopDialog"
        var addShopDialog: AddShopDialog? = null

        fun getInstance(tag: String): AddShopDialog {
            var bundle = Bundle().apply {
                putString(DataConstant.SHOP_TYPE_TAG, tag)
            }
            if (addShopDialog == null) {
                addShopDialog = AddShopDialog()
            }
            addShopDialog?.arguments = bundle
            return addShopDialog as AddShopDialog
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
        var view = inflater.inflate(R.layout.dialog_addshop, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var addShopNameTvEt = view.findViewById<TextInputLayout>(R.id.addShopNameTvEt)
        var addShopOpenTvEt = view.findViewById<TextInputLayout>(R.id.addShopOpenTvEt)
        var addShopPhoneTvEt = view.findViewById<TextInputLayout>(R.id.addShopPhoneTvEt)
        var addShopAddressTvEt = view.findViewById<TextInputLayout>(R.id.addShopAddressTvEt)
        var addShopPictureImg = view.findViewById<ImageView>(R.id.addShopPictureImg)
        var addShopAddBt = view.findViewById<Button>(R.id.addShopAddBt)
        progressDialog = ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }

        addShopAddBt.setOnClickListener {
            try {

                var ref = "tempShop"
                var tag = arguments?.getString(DataConstant.SHOP_TYPE_TAG)
                var shopName = addShopNameTvEt.editText?.text.toString()
                var open = addShopOpenTvEt.editText?.text.toString()
                var phone = addShopPhoneTvEt.editText?.text.toString()
                var address = addShopAddressTvEt.editText?.text.toString()
                var uid = FirebaseUnits.auth_getUser()?.uid

                if (!shopName.equals("")) {
                    progressDialog.show()
                    var tempShop = TempShop(tag!!, shopName, phone, open, uid!!, address)
                    FirebaseUnits.addTempData(ref, tempShop, PickPictureUnit.bitmapToByte(bitmap), callback)
                } else {
                    throw Exception("輸入不能為空")
                }
            } catch (e: Exception) {
                toast(e.message.toString() + "")
            }
        }

        addShopPictureImg.setOnClickListener {
            startActivityForResult(PickPictureUnit.pickIntent(), PickPictureUnit.REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PickPictureUnit.REQUEST_CODE) {
            bitmap = PickPictureUnit.uriToBitmap(activity!!, data)
            GlideApp.with(context!!)
                .load(data?.data)
                .into(addShopPictureImg)
        }
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