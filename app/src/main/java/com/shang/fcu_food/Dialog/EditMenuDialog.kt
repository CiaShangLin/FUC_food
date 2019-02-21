package com.shang.fcu_food.Dialog

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.shang.fcu_food.Data.Temp.TempMenu
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits
import com.shang.fcu_food.Unit.PickPictureUnit
import kotlinx.android.synthetic.main.dialog_editmenu.*
import org.jetbrains.anko.support.v4.toast

class EditMenuDialog : DialogFragment() {
    companion object {
        val TAG = "EditMenuDialog"
        private var editMenuDialog: EditMenuDialog? = null

        private val SHOP_NAME = "SHOP_NAME"
        private val MENU_NAME = "MENU_NAME"
        private val PRICE = "PRICE"

        fun getInstance(shopName: String, menuName: String, price: Int): EditMenuDialog {

            if (editMenuDialog == null) {
                editMenuDialog = EditMenuDialog()
            }
            editMenuDialog?.arguments = Bundle().apply {
                this.putString(SHOP_NAME, shopName)
                this.putString(MENU_NAME, menuName)
                this.putInt(PRICE, price)
            }

            return editMenuDialog!!
        }

    }

    private var bitmap: Bitmap? = null
    private lateinit var progressDialog: ProgressDialog
    private var callback = object : FirebaseCallback {
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
        var view = inflater.inflate(R.layout.dialog_editmenu, container, false)

        var editMenuNameTvEt = view.findViewById<TextInputLayout>(R.id.editMenuNameTvEt).apply {
            this.editText?.setText(arguments?.getString(MENU_NAME))
        }
        var editMenuPirceTvEt = view.findViewById<TextInputLayout>(R.id.editMenuPirceTvEt).apply {
            this.editText?.setText(arguments?.getInt(PRICE).toString())
        }
        var editMenuPictureImg = view.findViewById<ImageView>(R.id.editMenuPictureImg)
        var editMenuBt = view.findViewById<Button>(R.id.editMenuBt)

        progressDialog = ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editMenuPictureImg.setOnClickListener {
            startActivityForResult(PickPictureUnit.pickIntent(), PickPictureUnit.REQUEST_CODE)
        }

        editMenuBt.setOnClickListener {
            var shop_name = arguments?.getString(SHOP_NAME)
            var menu_name = editMenuNameTvEt.editText?.text.toString()
            var price = editMenuPirceTvEt.editText?.text.toString().toInt()
            var uid = FirebaseUnits.auth_getUser()?.uid
            var tempMenu = TempMenu(shop_name!!, menu_name, price, uid!!)

            progressDialog.show()
            FirebaseUnits.addTempData(tempMenu, PickPictureUnit.bitmapToByte(bitmap), callback)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PickPictureUnit.REQUEST_CODE -> {
                bitmap = PickPictureUnit.uriToBitmap(activity!!, data)
                GlideApp.with(context!!)
                    .load(data?.data)
                    .into(editMenuPictureImg)
            }
        }

    }

}