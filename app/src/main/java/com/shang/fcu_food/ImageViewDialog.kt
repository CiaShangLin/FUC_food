package com.shang.fcu_food

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ImageViewDialog : DialogFragment() {

    companion object {
        var imageViewDialog:DialogFragment?=null
        fun instance():ImageViewDialog{
            if(imageViewDialog==null){
                imageViewDialog=ImageViewDialog()
            }
            return imageViewDialog as ImageViewDialog
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.dialog_imageview,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}