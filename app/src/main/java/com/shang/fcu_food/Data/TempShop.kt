package com.shang.fcu_food.Data

class TempShop {
    var shopname:String=""
    var phone:String="無提供"
    var open:String="無提供"
    var uid: String = ""
    var latlng:String="無提供"

    constructor()
    constructor(shopname: String, phone: String, open: String, uid: String, latlng: String) {
        this.shopname = shopname
        this.phone = phone
        this.open = open
        this.uid = uid
        this.latlng = latlng
    }


}