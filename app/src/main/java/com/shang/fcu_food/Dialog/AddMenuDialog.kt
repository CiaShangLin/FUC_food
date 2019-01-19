package com.shang.fcu_food.Dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import kotlinx.android.synthetic.main.dialog_addmenu.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.io.File
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

    lateinit var b:Bitmap
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


                Log.d("TAG",b.width.toString()+" "+b.height.toString())
            }catch (e:Exception){
                toast("輸入錯誤")
            }

        }

        addMenuPictureIg.setOnClickListener {
            var intent= Intent().apply {
                this.setType("image/*")
                this.action=Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent,"請選擇食物圖片"),1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1){
            var uri=data?.data
            b=MediaStore.Images.Media.getBitmap(activity?.getContentResolver(),uri)

            GlideApp.with(context!!)
                .load(uri)
                .into(addMenuPictureIg)

            Log.d(TAG,b.byteCount.toString())

            var option=BitmapFactory.Options()
            option.inJustDecodeBounds=true
            var len=Math.min(b.width,b.height)
            var s=0
            if(len>720){
                var r=len/720.0
                s=r.toInt()
            }
            option.inJustDecodeBounds=false
            option.inSampleSize=s

            var byteArrayOutputStream=ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            var byte=byteArrayOutputStream.toByteArray()
            var c=BitmapFactory.decodeByteArray(byte,0,byte.size,option)

            Log.d(TAG,c.width.toString()+" "+c.height.toString())
            Log.d(TAG,c.byteCount.toString())
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