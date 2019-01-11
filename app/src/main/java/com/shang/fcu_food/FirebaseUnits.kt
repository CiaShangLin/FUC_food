package com.shang.fcu_food

import android.app.Activity
import android.content.Context
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.toast

class FirebaseUnits {


    companion object {
        val AUTH_RQEUESTCODE = 100

        fun checkHasAuth(): Boolean {
            return FirebaseAuth.getInstance().currentUser != null
        }

        fun auth(activity: Activity) {
            val providers =
                arrayListOf<AuthUI.IdpConfig>(AuthUI.IdpConfig.EmailBuilder().build())

            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), AUTH_RQEUESTCODE
            )
        }

        fun database_addShop(){
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("breakfast/4")
            var map = mutableMapOf<String, Any>()
            map.put("address", "EEE")
            map.put("name", "EEE")
            myRef.updateChildren(map)

            myRef.addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    Log.d("TAG Change", p1 + " " + p0.key + " " + p0.value)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.d("TAG",p0.key+" "+p0.value)
                }
            })
        }
    }
}