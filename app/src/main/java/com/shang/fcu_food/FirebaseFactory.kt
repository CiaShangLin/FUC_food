package com.shang.fcu_food

class FirebaseFactory {

    companion object {
        private val myFirebaseAuth: MyFirebaseAuth = MyFirebaseAuth()
        fun getMyFirebaseAuth(): MyFirebaseAuth = myFirebaseAuth

        private val myFirebaseDatabase: MyFirebaseDatabase = MyFirebaseDatabase()
        fun getMyFirebaseDatabase(): MyFirebaseDatabase = myFirebaseDatabase

        private var myFirebaseStorage: MyFirebaseStorage = MyFirebaseStorage()
        fun getMyFirebaseStorage(): MyFirebaseStorage = myFirebaseStorage
    }
}