package com.shang.fcu_food.Unit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shang.fcu_food.Version
import org.jetbrains.anko.alert

class VersionCheckUnit {

    companion object {
        val VERSION_CHECK_NEW = 180

        fun checkVersion(context: Context, handler: Handler) {
            var verison = FirebaseDatabase.getInstance().getReference("version")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showErrorAlert(context, p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var versionCode =  //目前App的版本
                            context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toString()
                        var version = p0.getValue(Version::class.java)!!      //firebase上的版本
                        var flag =//假設使用者先更新好了 但我的version還沒上去更新
                            (version?.release.equals(versionCode)) || (versionCode.toInt() > version.release.toInt())
                        if (flag) {
                            handler.handleMessage(Message().apply {
                                this.what = VERSION_CHECK_NEW
                            })
                        } else {
                            showUpdateAlert(context, version?.information.toString())
                        }
                    }
                })
        }

        private fun showUpdateAlert(context: Context, message: String) {
            context.alert {
                this.title = "請問要前往更新嗎"
                this.message = message
                this.positiveButton("前往", onClicked = {
                    context.startActivity(Intent().apply {
                        this.action = Intent.ACTION_VIEW
                        this.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.shang.fcu_food"))
                    })
                })
                this.isCancelable = false
            }.show()
        }

        private fun showErrorAlert(context: Context, message: String) {
            context.alert {
                this.title = "ERROR"
                this.message = "發生了未知錯誤\n" + message
            }.show()
        }
    }

}