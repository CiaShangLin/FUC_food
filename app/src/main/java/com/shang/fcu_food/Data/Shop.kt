package com.shang.fcu_food.Data

open class Shop {
    var id: Int = 0                 //根結點 EX:1
    var name: String = ""          //店名
    var address: String = ""        //地址
    var latlng: String = " "         //經緯度
    var phone: String = ""         //電話
    var time: String = ""          //營業時間

    var star: Double = -1.0           //這個應該從Menu計算出來
    var picture: String = ""          //可能從name去storage讀取 可能是個LIST
    var menuPicture: String = ""     //菜單照片
    var dishesList: MutableList<Dishes> = mutableListOf<Dishes>()       //這個店的菜品

    constructor()

}