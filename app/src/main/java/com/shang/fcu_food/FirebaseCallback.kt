package com.shang.fcu_food

interface FirebaseCallback {
    fun statusCallBack(database_status:Boolean,storage_status:Boolean)
}