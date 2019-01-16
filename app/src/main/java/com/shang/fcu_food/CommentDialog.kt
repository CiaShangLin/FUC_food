package com.shang.fcu_food

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater

class CommentDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view=LayoutInflater.from(context).inflate(R.layout.dialog_addcomment,null)

        var alertDialog=AlertDialog.Builder(context)
            .setTitle("輸入評論")
            .setView(view)
            .create()
        return alertDialog
    }
}