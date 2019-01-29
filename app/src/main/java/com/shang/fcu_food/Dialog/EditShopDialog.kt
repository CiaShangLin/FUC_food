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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.FirebaseCallback
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits
import com.shang.fcu_food.Unit.PickPictureUnit
import kotlinx.android.synthetic.main.dialog_editshop.*
import kotlinx.android.synthetic.main.dialog_editshop.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast

class EditShopDialog : DialogFragment() {

    companion object {
        val TAG="EditShopDialog"
        var editShopDialog:EditShopDialog?=null

        fun getInstance(shop: Shop):EditShopDialog{
            if(editShopDialog==null) {
                editShopDialog = EditShopDialog()
            }
            editShopDialog?.arguments= getBundle(shop)
            return editShopDialog as EditShopDialog
        }

        private fun getBundle(shop:Shop):Bundle=Bundle().apply {
            this.putString("name",shop.name)
            this.putString("time",shop.time)
            this.putString("phone",shop.phone)
            this.putString("address",shop.address)
        }
    }

    var bitmap: Bitmap? = null
    val tag= arrayListOf<String>(BreakfastShop.tag, DinnerShop.tag, SnackShop.tag, DrinkShop.tag)
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
        var view=inflater.inflate(R.layout.dialog_editshop,container,false)
        var editShopNameTvEt=view.findViewById<TextInputLayout>(R.id.editShopNameTvEt)
        var editShopOpenTvEt=view.findViewById<TextInputLayout>(R.id.editShopOpenTvEt)
        var editShopPhoneTvEt=view.findViewById<TextInputLayout>(R.id.editShopPhoneTvEt)
        var editShopAddressTvEt=view.findViewById<TextInputLayout>(R.id.editShopAddressTvEt)
        var editShopTagSp=view.findViewById<Spinner>(R.id.editShopTagSp).apply {
            this.adapter= ArrayAdapter.createFromResource(
                context,R.array.dialog_addshop_tagSpinner, android.R.layout.simple_spinner_dropdown_item)
        }

        var editShopMenuImg=view.findViewById<ImageView>(R.id.editShopMenuImg)
        var editShopBt=view.findViewById<Button>(R.id.editShopBt)
        progressDialog = ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }

        editShopNameTvEt.editText?.setText(arguments?.getString("name"))
        editShopOpenTvEt.editText?.setText(arguments?.getString("time"))
        editShopPhoneTvEt.editText?.setText(arguments?.getString("phone"))
        editShopAddressTvEt.editText?.setText(arguments?.getString("address"))


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editShopMenuImg.setOnClickListener {
            startActivityForResult(PickPictureUnit.pickIntent(), PickPictureUnit.REQUEST_CODE)
        }

        editShopBt.setOnClickListener {
            try{
                var tempShop=TempShop(
                    tag[editShopTagSp.selectedItemPosition],
                    editShopNameTvEt.editText?.text.toString(),
                    editShopPhoneTvEt.editText?.text.toString(),
                    editShopOpenTvEt.editText?.text.toString(),
                    FirebaseUnits.auth_getUser()?.uid!!,
                    editShopAddressTvEt.editText?.text.toString()
                )
                progressDialog.show()
                FirebaseUnits.addTempData("tempShop", tempShop, PickPictureUnit.bitmapToByte(bitmap), callback)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            PickPictureUnit.REQUEST_CODE->{
                bitmap = PickPictureUnit.uriToBitmap(activity!!, data)
                GlideApp.with(context!!)
                    .load(data?.data)
                    .into(editShopMenuImg)
            }
        }

    }
}