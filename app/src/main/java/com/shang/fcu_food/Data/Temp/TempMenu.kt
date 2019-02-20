package com.shang.fcu_food.Data.Temp

class TempMenu : TempData {

    override var ref: String = "tempMenu"
    override var uid: String = ""
    override var shop_name: String = ""

    var menu_name: String = ""
    var star: Double = 0.0
    var price: Int = 0
    var comment: String = "沒有輸入"

    constructor()
    //新增菜單用
    constructor(shop_name: String, menu_name: String, star: Double, price: Int, uid: String, comment: String) {
        this.shop_name = shop_name
        this.menu_name = menu_name
        this.star = star
        this.price = price
        this.uid = uid
        this.comment = comment
    }

    //修改菜單用
    constructor(shop_name: String, menu_name: String, price: Int, uid: String) {
        this.shop_name = shop_name
        this.menu_name = menu_name
        this.price = price
        this.uid = uid
    }


}