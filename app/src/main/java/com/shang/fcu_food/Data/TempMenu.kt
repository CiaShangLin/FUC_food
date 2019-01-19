package com.shang.fcu_food.Data

class TempMenu {
    var shopname: String = ""
    var menuname: String = ""
    var star: Double = 0.0
    var price: Int = 0
    var uid: String = ""
    var comment: String = ""

    constructor()
    constructor(shopname: String, menuname: String, star: Double, price: Int, uid: String, comment: String) {
        this.shopname = shopname
        this.menuname = menuname
        this.star = star
        this.price = price
        this.uid = uid
        this.comment = comment
    }


}