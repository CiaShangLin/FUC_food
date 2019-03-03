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
import com.google.android.gms.ads.AdView
import com.shang.fcu_food.DetailMenu.DetailMenuActivity
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.AdmobUnit
import com.shang.fcu_food.Unit.FirebaseUnits

class AddCommentDialog : DialogFragment() {

    companion object {
        val TAG: String = "AddCommentDialog"

        private val SHOP_TAG: String = "SHOP_TAG"
        private val SHOP_ID: String = "SHOP_ID"
        private val MENU_ID: String = "MENU_ID"
        private val COMMENT_SIZE: String = "COMMENT_SIZE"
        private var commentDialog: AddCommentDialog? = null

        fun getInstace(shop_tag: String, shop_id: Int, menu_id: Int, comment_size: Int): AddCommentDialog {
            if (commentDialog == null) {
                commentDialog = AddCommentDialog()
            }
            commentDialog?.arguments = Bundle().apply {
                this.putString(SHOP_TAG, shop_tag)
                this.putInt(SHOP_ID, shop_id)
                this.putInt(MENU_ID, menu_id)
                this.putInt(COMMENT_SIZE, comment_size)
            }
            return commentDialog!!
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_addcomment, null)

        var argument = arguments
        var shop_tag = argument?.getString(SHOP_TAG)
        var shop_id = argument?.getInt(SHOP_ID)
        var menu_id = argument?.getInt(MENU_ID)
        var comment_size = argument?.getInt(COMMENT_SIZE)
        val ref = "$shop_tag/$shop_id/menu/$menu_id/usercomment/$comment_size"
        Log.v(TAG, ref)

        var commentRatingBar = view.findViewById<RatingBar>(R.id.comment_RatingBar)
        var commentInputTextLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        var addcomment_adview = view.findViewById<AdView>(R.id.addcomment_adview)

        //AdmobUnit.getInstance(context!!)?.show(addcomment_adview)

        var alertDialog = AlertDialog.Builder(context)
            .setTitle(R.string.CommentDialog_Title)
            .setView(view)
            .setPositiveButton(R.string.CommentDialog_PositiveButton, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    var length = commentInputTextLayout.editText?.text?.length
                    if (length!! >= 0 && length <= 50) {
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