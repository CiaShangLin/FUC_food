package com.shang.fcu_food

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

class PermissionUnit {

    companion object {
        val TAG = "PermissionUnit"

        fun checkPermission(activity: Activity): Boolean {
            var permissionArray = arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )


            var noList = arrayListOf<String>()
            for (permission in permissionArray) {
                if (ContextCompat.checkSelfPermission(activity.baseContext, permission) != PackageManager.PERMISSION_GRANTED) {
                    noList.add(permission)
                }
            }
            Log.v(TAG,"未通過權限數量:"+noList.size)

            if (noList.size != 0) {
                ActivityCompat.requestPermissions(activity, noList.toArray(arrayOf()), 1)
            } else {
                return false
            }

            return true
        }


    }

}