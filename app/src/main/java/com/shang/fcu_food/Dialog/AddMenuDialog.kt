package com.shang.fcu_food.Dialog

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.TempMenu
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.Main.GlideApp
import com.shang.fcu_food.R
import com.shang.fcu_food.UploadAsy
import kotlinx.android.synthetic.main.dialog_addmenu.*
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.lang.Exception

class AddMenuDialog : DialogFragment() {

    companion object {
        val TAG="AddMenuDialog"

        var addMenuDialog: AddMenuDialog?=null
        fun getInstance(shopName:String) : AddMenuDialog {
            var bundle=Bundle().apply {
                this.putString(DataConstant.SHOP_NAME,shopName)
            }
            if(addMenuDialog ==null){
                addMenuDialog = AddMenuDialog()
            }
            addMenuDialog?.arguments=bundle
            return addMenuDialog!!
        }
    }

    var bitmap:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.dialog_addmenu,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var shop_name=arguments?.getString(DataConstant.SHOP_NAME)

        var addMenuNameTvEt=view.findViewById<TextInputLayout>(R.id.addMenuNameTvEt)
        var addMenuPriceTvEt=view.findViewById<TextInputLayout>(R.id.addMenuPriceTvEt)
        var addMenuCommentTvEt=view.findViewById<TextInputLayout>(R.id.addMenuCommentTvEt)
        var addMenuRatingBar=view.findViewById<RatingBar>(R.id.addMenuRatingBar)
        var addMenuPictureIg=view.findViewById<ImageView>(R.id.addMenuPictureIg)
        var addMenuAddBt=view.findViewById<Button>(R.id.addMenuAddBt)

        addMenuAddBt.setOnClickListener {
            try{
                var ref="tempMenu"
                var menu_name=addMenuNameTvEt.editText?.text.toString()
                var star=addMenuRatingBar.rating.toDouble()
                var price=addMenuPriceTvEt.editText?.text.toString().toInt()
                var uid=FirebaseUnits.auth_getUser()?.uid
                var comment=addMenuCommentTvEt.editText?.text.toString()
                var tempMenu=TempMenu(shop_name!!,menu_name!!,star,price,uid!!,comment)

                //FirebaseUnits.database_addMenu(ref,tempMenu,activity as Activity)

                UploadAsy(context!!, byteArrayOf()).execute()
            }catch (e:Exception){
                toast("輸入錯誤")
            }

        }

        addMenuPictureIg.setOnClickListener {
            var intent= Intent().apply {
                this.setType("image/*")
                this.action=Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1){
            var uri=data?.data
            bitmap=MediaStore.Images.Media.getBitmap(activity?.getContentResolver(),uri)
            Log.d(TAG,bitmap?.byteCount.toString())

            GlideApp.with(context!!)
                .load(uri)
                .into(addMenuPictureIg)

            var c=Bitmap.createScaledBitmap(bitmap,480,720,true)
            Log.d(TAG,c.byteCount.toString())



            var file=FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            +"/temp.jpeg")
            c.compress(Bitmap.CompressFormat.JPEG,100,file)

            var byteArrayOutputStream = ByteArrayOutputStream()
            c.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
            var byte=byteArrayOutputStream.toByteArray()

            FirebaseUnits.storage_addMenuImage(byte)

        }
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