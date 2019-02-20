package com.shang.fcu_food.Data.Temp

class TempShop : TempData {

    override var ref: String="tempShop"
    override var uid: String = ""
    override var shop_name: String=""

    var tag:String=""
    var phone:String="無提供"
    var open:String="無提供"
    var latlng:String="無提供"

    constructor()
    constructor(tag:String,shopname: String, phone: String, open: String, uid: String, latlng: String) {
        this.tag=tag
        this.shop_name = shopname
        this.phone = phone
        this.open = open
        this.uid = uid
        this.latlng = latlng
    }


}