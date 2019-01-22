package com.shang.fcu_food.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.widget.RatingBar
import com.shang.fcu_food.DetailMenu.DetailMenuActivity
import com.shang.fcu_food.Unit.FirebaseUnits
import com.shang.fcu_food.R

class AddCommentDialog : DialogFragment() {

    companion object {
        val TAG: String = "AddCommentDialog"
        val MENU_ID: String = "MENU_ID"
        val COMMENT_SIZE: String = "COMMENT_SIZE"
        var commentDialog: AddCommentDialog? = null

        fun getInstace(menu_id: String, comment_size: String): AddCommentDialog {
            var bundle = Bundle()
            bundle.putString(MENU_ID, menu_id)
            bundle.putString(COMMENT_SIZE, comment_size)

            if (commentDialog == null) {
                commentDialog = AddCommentDialog()
            }
            commentDialog?.arguments = bundle
            return commentDialog!!
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_addcomment, null)

        var argument = arguments
        var menu_id = argument?.getString(MENU_ID)
        var comment_size = argument?.getString(COMMENT_SIZE)
        val ref =
            "${DetailMenuActivity.shop_type_tag}/${DetailMenuActivity.shop_id}/menu/$menu_id/usercomment/$comment_size"
        Log.v(TAG, ref)

        var commentRatingBar = view.findViewById<RatingBar>(R.id.comment_RatingBar)
        var commentInputTextLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)

        var alertDialog = AlertDialog.Builder(context)
            .setTitle(R.string.CommentDialog_Title)
            .setView(view)
            .setPositiveButton(R.string.CommentDialog_PositiveButton, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    var length=commentInputTextLayout.editText?.text?.length
                    if(length!! >= 0 && length<=50){
                        FirebaseUnits.database_addCommemt(
                            ref,
                            commentRatingBar.rating.toString(),
                            commentInputTextLayout.editText?.text.toString(),
                            FirebaseUnits.auth_getUser()?.uid!!,
                            activity!!
                        )
                    }
                }
            })
            .setNegativeButton(R.string.CommentDialog_NegativeButton, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }
            })
            .create()
        return alertDialog
    }
}