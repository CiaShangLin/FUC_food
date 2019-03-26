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
import android.widget.RatingBar
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.Temp.TempMenu
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.PickPictureUnit
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.dialog_addmenu.*
import org.jetbrains.anko.support.v4.toast

class AddMenuDialog : DialogFragment() {

    companion object {
        val TAG = "AddMenuDialog"
        private var addMenuDialog: AddMenuDialog? = null

        fun getInstance(shopName: String): AddMenuDialog {
            if (addMenuDialog == null) {
                addMenuDialog = AddMenuDialog()
            }
            addMenuDialog?.arguments = Bundle().apply {
                this.putString(DataConstant.SHOP_NAME, shopName)
            }
            return addMenuDialog!!
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
    private var consumer = object : Consumer<Boolean?> {
        override fun accept(accept: Boolean?) {
            if (accept != null) {
                if (accept) {
                    toast("新增成功")
                    dismiss()
                } else {
                    toast("新增失敗")
                }
                progressDialog.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_addmenu, container, false)
        var addMenuShopNameTvEt = view.findViewById<TextInputLayout>(R.id.addMenuShopNameTvEt).apply {
            this.editText?.setText(arguments?.getString(DataConstant.SHOP_NAME))
        }
        var addMenuNameTvEt = view.findViewById<TextInputLayout>(R.id.addMenuNameTvEt)
        var addMenuPriceTvEt = view.findViewById<TextInputLayout>(R.id.addMenuPriceTvEt)
        var addMenuCommentTvEt = view.findViewById<TextInputLayout>(R.id.addMenuCommentTvEt)
        var addMenuRatingBar = view.findViewById<RatingBar>(R.id.addMenuRatingBar)
        var addMenuPictureIg = view.findViewById<ImageView>(R.id.addMenuPictureIg)
        var addMenuAddBt = view.findViewById<Button>(R.id.addMenuAddBt)

        progressDialog = ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addMenuAddBt.setOnClickListener {
            try {
                var shop_name = addMenuShopNameTvEt.editText?.text.toString()
                var menu_name = addMenuNameTvEt.editText?.text.toString()
                var star = addMenuRatingBar.rating.toDouble()
                var price = addMenuPriceTvEt.editText?.text.toString().toInt()
                var uid = FirebaseFactory.getMyFirebaseAuth().auth_getUser()?.uid
                var comment = addMenuCommentTvEt.editText?.text.toString()
                var tempMenu = TempMenu(shop_name, menu_name, star, price, uid!!, comment)

                if (menu_name.length == 0 || comment.length == 0) {
                    throw Exception("輸入錯誤")
                } else {
                    progressDialog.show()
                    FirebaseFactory.getMyFirebaseDatabase()
                        .addTempData(tempMenu, PickPictureUnit.bitmapToByte(bitmap), callback)
                }

            } catch (e: Exception) {
                toast(e.message + "")
            }
        }

        addMenuPictureIg.setOnClickListener {
            startActivityForResult(PickPictureUnit.pickIntent(), PickPictureUnit.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PickPictureUnit.REQUEST_CODE) {

            bitmap = PickPictureUnit.uriToBitmap(activity!!, data)
            GlideApp.with(context!!)
                .load(data?.data)
                .into(addMenuPictureIg)
        }
    }


}