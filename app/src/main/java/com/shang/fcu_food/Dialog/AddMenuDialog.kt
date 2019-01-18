package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.R

class AddMenuDialog : DialogFragment() {

    companion object {
        val TAG="AddMenuDialog"



        var addMenuDialog: AddMenuDialog?=null
        fun getInstance(shopName:String,menuName:String) : AddMenuDialog {
            var bundle=Bundle().apply {
                this.putString(DataConstant.SHOP_NAME,shopName)
                this.putString(DataConstant.MENU_NAME,menuName)
            }
            if(addMenuDialog ==null){
                addMenuDialog = AddMenuDialog()
            }
            addMenuDialog?.arguments=bundle
            return addMenuDialog!!
        }
    }

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
        var menu_name=arguments?.getString(DataConstant.MENU_NAME)

        var addMenuNameTvEt=view.findViewById<TextInputLayout>(R.id.addMenuNameTvEt)
        var addMenuPriceTvEt=view.findViewById<TextInputLayout>(R.id.addMenuPriceTvEt)
        var addMenuCommentTvEt=view.findViewById<TextInputLayout>(R.id.addMenuCommentTvEt)
        var addMenuRatingBar=view.findViewById<RatingBar>(R.id.addMenuRatingBar)
        var addMenuPictureIg=view.findViewById<ImageView>(R.id.addMenuPictureIg)



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