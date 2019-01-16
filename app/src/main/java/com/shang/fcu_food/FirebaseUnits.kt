package com.shang.fcu_food

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.Main.GlideApp
import org.jetbrains.anko.toast

class FirebaseUnits {


    companion object {
        val AUTH_RQEUESTCODE = 100

        fun checkHasAuth(): Boolean {
            return FirebaseAuth.getInstance().currentUser != null
        }

        fun auth_Login(activity: Activity) {
            val providers =
                arrayListOf<AuthUI.IdpConfig>(AuthUI.IdpConfig.EmailBuilder().build())

            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), AUTH_RQEUESTCODE
            )
        }

        fun storage_loadImg(context: Context, img: ImageView, tag: String, name: String, option: RequestOptions) {
            var ref = FirebaseStorage.getInstance().getReference(tag).child(name).child("$name.JPG")
            //var ref=FirebaseStorage.getInstance().getReference(tag).child("御方香.JPG")
            ref.downloadUrl.addOnSuccessListener {
                GlideApp.with(context).load(it).apply(option).into(img)
            }.addOnFailureListener {
                Log.d("TAG", "載入失敗" + it.toString())
            }
        }

        fun database_addCommemt(tag: String, shop_id: String, menu_id: String, comment_size: String, comment: String) {
            val ref_path = "$tag/$shop_id/menu/$menu_id/usercomment/$comment_size"
            val ref = FirebaseDatabase.getInstance().getReference().child(ref_path)
            Log.d("TAG",ref_path)
            var map = mutableMapOf<String, Any>()
            map.put("comment",comment)
            map.put("uid","XXX")
            map.put("star",4.0)
            ref.updateChildren(map)
        }
    }
}