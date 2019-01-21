package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.shang.fcu_food.R
import java.lang.Exception

class AddShopDialog :DialogFragment() {

    companion object {
        val TAG="AddShopDialog"
        var addShopDialog:AddShopDialog?=null

        fun getInstance():AddShopDialog{
            if(addShopDialog==null){
                addShopDialog= AddShopDialog()
            }
            return addShopDialog as AddShopDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.dialog_addshop,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var addShopNameTvEt=view.findViewById<TextInputLayout>(R.id.addShopNameTvEt)
        var addShopOpenTvEt=view.findViewById<TextInputLayout>(R.id.addShopNameTvEt)
        var addShopPhoneTvEt=view.findViewById<TextInputLayout>(R.id.addShopPhoneTvEt)
        var addShopAddressTvEt=view.findViewById<TextInputLayout>(R.id.addShopAddressTvEt)
        var addShopPictureImg=view.findViewById<ImageView>(R.id.addShopPictureIg)
        var addShopAddBt=view.findViewById<Button>(R.id.addShopAddBt)

        addShopAddBt.setOnClickListener {
            try{
                var shopName=addShopNameTvEt.editText?.text.toString()
                var open=addShopOpenTvEt.editText!!.text.toString()
                var phone=addShopPhoneTvEt.editText!!.text.toString()
                var address=addShopAddressTvEt.editText!!.text.toString()
                Log.d(TAG,"$shopName $open $phone $address")
            }catch (e:Exception){
                e.printStackTrace()
            }
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