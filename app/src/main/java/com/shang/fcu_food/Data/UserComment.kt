package com.shang.fcu_food.Data

import com.google.firebase.database.Exclude

class UserComment {
    var uid: String = ""           //使用者的UID或是帳號
    var picture: String = ""        //使用者的照片
    var comment: String = ""      //使用者給予的評價
    var star: Double = -1.0        //使用者給予的星數
    constructor()

    @Exclude
    fun toMap():MutableMap<String,Any>{

        var map= mutableMapOf<String,Any>()
        map.put("uid",uid)
        map.put("star",star)
        map.put("comment",comment)

        return map
    }
}