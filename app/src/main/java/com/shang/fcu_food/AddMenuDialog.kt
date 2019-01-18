package com.shang.fcu_food

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar

class AddMenuDialog : DialogFragment() {

    companion object {
        val TAG="AddMenuDialog"

        var addMenuDialog:AddMenuDialog?=null
        fun getInstance() : AddMenuDialog{
            if(addMenuDialog==null){
                addMenuDialog= AddMenuDialog()
            }
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