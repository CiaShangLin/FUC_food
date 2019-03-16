package com.shang.fcu_food.Data.menu

import android.content.Context
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.Query
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FileStorageUnit
import java.io.File

open class Menu : FirebaseMenu {


    var shop_id: Int = -1
    var shop_tag: String = ""
    var shop_name: String = ""

    var id: Int = -1
    var name: String = ""       //菜名
    var star: Double = 0.0       //這項菜的平均星數 從User裡算出來
        get() {
            var total = 0.0
            for (comment in usercomment) {
                total += comment.star
            }
            if (total != 0.0)
                return total / usercomment.size
            return 0.0
        }

    var price: Int = 0           //價格
    var usercomment: MutableList<UserComment> = mutableListOf<UserComment>()
    open var errorDrawable: Int = R.drawable.ic_breakfast

    constructor()

    override fun getQuery(shop_tag: String, shop_id: Int): Query? {
        return null
    }

    override fun getOption(shop_tag: String, shop_name: String, shop_id: Int): FirebaseRecyclerOptions<Menu>? {
        return null
    }

    override fun getSnapParser(shop_tag: String, shop_name: String, shop_id: Int): SnapshotParser<Menu>? {
        return null
    }

    fun getFile(context: Context): File {
        return FileStorageUnit.createFile(context, shop_tag, shop_name, name)
    }

    fun getStorageRef(): StorageReference {
        return FirebaseFactory.getMyFirebaseStorage().storage_getImageRef(shop_tag, shop_name, name)
    }

}