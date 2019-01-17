package com.shang.fcu_food.Data

open class Shop {
    var id: Int = -1                 //根結點 EX:1
    var name: String = ""          //店名
    var address: String = ""        //地址
    var latlng: String = " "         //經緯度
    var phone: String = "尚無提供"         //電話
    var time: String = "尚無提供"          //營業時間
    var picture: String = "尚無提供"          //可能從name去storage讀取 可能是個LIST
    var menuPicture: String = ""     //菜單照片
    var menu: MutableList<Menu> = mutableListOf<Menu>()       //這個店的菜品

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


}