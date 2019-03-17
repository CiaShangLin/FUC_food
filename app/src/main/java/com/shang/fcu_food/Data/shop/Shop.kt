package com.shang.fcu_food.Data.shop

import android.content.Context
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Query
import com.google.firebase.storage.StorageReference
import com.shang.fcu_food.Data.menu.Menu
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FileStorageUnit
import java.io.File

abstract open class Shop : FirebaseShop {
    companion object {
        val BREAKFAST_SHOP = "breakfast"
        val DINNER_SHOP = "dinner"
        val DRINK_SHOP = "drink"
        val SNACK_SHOP = "snack"
        val SHOP_LIST = arrayOf(BREAKFAST_SHOP, DINNER_SHOP, SNACK_SHOP, DRINK_SHOP)
        fun getShopType(shop_tag: String): Shop? {
            when (shop_tag) {
                BREAKFAST_SHOP -> return BreakfastShop()
                DINNER_SHOP -> return DinnerShop()
                DRINK_SHOP -> return DrinkShop()
                SNACK_SHOP -> return SnackShop()
            }
            return null
        }
    }

    var id: Int = -1                 //根結點 EX:1
    var name: String = ""          //店名
    var address: String = ""        //地址 經緯度
    var phone: String = "尚無提供"         //電話
    var time: String = "尚無提供"          //營業時間
    open var menu: MutableList<Menu> = mutableListOf()    //這個店的菜品
    open var errorDrawable: Int = R.drawable.ic_shop  //錯誤圖片
    abstract open var shop_tag: String //店的類型
    var place_id: String = ""   //Google Place ID


    constructor()

    var star: Double = -1.0           //這個應該從Menu計算出來
        get() {
            var total: Double = 0.0
            var count = 0
            for (m in menu) {
                for (s in m.usercomment) {
                    count++
                    total += s.star
                }
            }
            if (count == 0) {
                return 0.0
            }
            return total / count
        }

    abstract override fun getQuery(): Query

    abstract override open fun getOption(): FirebaseRecyclerOptions<Shop>

    abstract override open fun getSnapParser(): SnapshotParser<Shop>

    fun getFile(context: Context): File {
        return FileStorageUnit.createFile(context, shop_tag, name, name)
    }

    fun getStorageRef(): StorageReference {
        return FirebaseFactory.getMyFirebaseStorage().storage_getImageRef(shop_tag, name, name)
    }

    fun getLatLng(): LatLng {
        //如果地址為空的話 他的size會是1
        var latlng: List<String> = address.split(",")
        if (latlng.size == 2) {
            return LatLng(latlng?.get(0)!!.toDouble(), latlng.get(1).toDouble())
        }
        return LatLng(0.0, 0.0)
    }

}