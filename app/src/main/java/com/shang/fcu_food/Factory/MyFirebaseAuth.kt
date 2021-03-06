package com.shang.fcu_food.Factory

import android.app.Activity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.DataBind

class MyFirebaseAuth {

    val AUTH_RQEUESTCODE = 100
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkHasAuth(): Boolean {  //檢查是否已經登入
        return firebaseAuth.currentUser != null
    }

    fun auth_Login(activity: Activity) {   //註冊與登入
        val providers =
            arrayListOf<AuthUI.IdpConfig>(AuthUI.IdpConfig.EmailBuilder().build())

        activity.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), AUTH_RQEUESTCODE
        )
    }

    //取得user
    fun auth_getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun auth_uidToUser(): User? {
        return DataBind.allUser.get(auth_getUser()?.uid)
    }

}