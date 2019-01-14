package com.shang.fcu_food.Data

class Menu {
    var name: String = ""       //菜名
    var star: Double = -1.0       //這項菜的平均星數 從User裡算出來
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
    var picture: String = ""     //照片 可能是一個List
    var usercomment: MutableList<UserComment> = mutableListOf<UserComment>()

    constructor()


}