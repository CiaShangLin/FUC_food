package com.shang.fcu_food.Factory

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MyFirebaseStorage {
    private val firebaseStorage=FirebaseStorage.getInstance()

    //取得圖片的StorageReference
    fun storage_getImageRef(shop_tag: String, shop_name: String, image_name: String): StorageReference {
        return firebaseStorage.getReference(shop_tag).child(shop_name).child("$image_name.jpg")
    }

}