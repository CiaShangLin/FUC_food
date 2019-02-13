package com.shang.fcu_food.Data.menu

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.R

open class Menu {
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

    open fun getQuery(shop_tag: String, shop_id: String): Query? {
        return null
    }

    open fun getOption(shop_tag: String, shop_id: String): FirebaseRecyclerOptions<Menu>? {
        return null
    }
}