package com.shang.fcu_food.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shang.fcu_food.R

class LoadingDialog : DialogFragment() {

    companion object {
        val TAG: String = "LoadingDialog"
        private var loadingDialog: LoadingDialog? = null
        fun getInstance(): LoadingDialog {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog()
            }
            return loadingDialog as LoadingDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
    }
}