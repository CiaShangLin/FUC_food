package com.shang.fcu_food.Data

class User {
    var uid:String=""
    var name:String=""
    var picture:String="1"
    var sex:String="man"


    constructor()

    override fun toString(): String {
        return "$name $sex $uid $picture"
    }
}