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
import com.google.android.gms.maps.model.LatLng
import com.shang.fcu_food.Data.Temp.TempShop
import com.shang.fcu_food.Data.shop.*
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.Maps.MapsActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.PickPictureUnit
import kotlinx.android.synthetic.main.dialog_addshop.*
import org.jetbrains.anko.support.v4.toast


class AddShopDialog : DialogFragment() {

    companion object {
        val TAG = "AddShopDialog"
        private var addShopDialog: AddShopDialog? = null

        fun getInstance(): AddShopDialog {
            if (addShopDialog == null) {
                addShopDialog = AddShopDialog()
            }
            return addShopDialog as AddShopDialog
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
        var view = inflater.inflate(R.layout.dialog_addshop, container, false)
        var addShopNameTvEt = view.findViewById<TextInputLayout>(R.id.addShopNameTvEt)
        var addShopOpenTvEt = view.findViewById<TextInputLayout>(R.id.addShopOpenTvEt)
        var addShopPhoneTvEt = view.findViewById<TextInputLayout>(R.id.addShopPhoneTvEt)
        var addShopAddressTvEt = view.findViewById<TextInputLayout>(R.id.addShopAddressTvEt)
        var addShopPictureImg = view.findViewById<ImageView>(R.id.addShopPictureImg)
        var addShopAddBt = view.findViewById<Button>(R.id.addShopAddBt)
        var addShopTagSp=view.findViewById<Spinner>(R.id.addShopTagSp).apply {
            this.adapter=ArrayAdapter.createFromResource(
                context,R.array.dialog_addshop_tagSpinner, android.R.layout.simple_spinner_dropdown_item)
        }
        var addShopGoogleMapImg=view.findViewById<ImageView>(R.id.addShopGoogleMapImg)


        progressDialog = ProgressDialog(context).apply {
            this.setCancelable(false)
            this.setTitle("上傳中...")
            this.setMessage("努力上傳中")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addShopAddBt.setOnClickListener {
            try {
                var tag = Shop.SHOP_LIST[addShopTagSp.selectedItemPosition]
                var shopName = addShopNameTvEt.editText?.text.toString()
                var open = addShopOpenTvEt.editText?.text.toString()
                var phone = addShopPhoneTvEt.editText?.text.toString()
                var address = addShopAddressTvEt.editText?.text.toString()
                var uid = FirebaseFactory.getMyFirebaseAuth().auth_getUser()?.uid

                if (!shopName.equals("")) {
                    progressDialog.show()
                    var tempShop = TempShop(tag!!, shopName, phone, open, uid!!, address)
                    FirebaseFactory.getMyFirebaseDatabase().addTempData(tempShop, PickPictureUnit.bitmapToByte(bitmap), callback)
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

        addShopGoogleMapImg.setOnClickListener {
            startActivityForResult(Intent(activity, MapsActivity::class.java).apply {
                this.action=AddShopDialog::class.java.simpleName
            }
                , MapsActivity.REQUEST_CODE_LATLNG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            PickPictureUnit.REQUEST_CODE->{
                bitmap = PickPictureUnit.uriToBitmap(activity!!, data)
                GlideApp.with(context!!)
                    .load(data?.data)
                    .into(addShopPictureImg)
            }

            MapsActivity.REQUEST_CODE_LATLNG -> {
                if (data?.extras != null) {
                    var latlng = data?.extras.get(MapsActivity.LATLNG) as LatLng
                    addShopAddressTvEt.editText?.setText(
                        String.format("%.4f,%.4f", latlng.latitude, latlng.longitude)
                    )
                }
            }
        }

    }

}