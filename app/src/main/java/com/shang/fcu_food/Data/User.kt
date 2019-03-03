package com.shang.fcu_food.Data

import com.google.firebase.auth.FirebaseUser

class User {
    var uid: String = ""
    var name: String = ""
    var picture: String = "1"  // 1=Cat 2=Dog
    var sex: String = "man"


    constructor()

    constructor(uid: String, name: String, picture: String, sex: String) {
        this.uid = uid
        this.name = name
        this.picture = picture
        this.sex = sex
    }


    override fun toString(): String {
        return "$name $sex $uid $picture"
    }

    //只有註冊的時候用到
    fun getUser(firebaseUser: FirebaseUser): User {
        name = firebaseUser.displayName!!
        uid = firebaseUser.uid
        sex = sex
        picture = randomPicture()
        return this
    }

    fun toMap(): MutableMap<String, Any> {
        var map = mutableMapOf<String, Any>()
        map.put("name", name)
        map.put("picture", picture)
        map.put("sex", sex)
        return map
    }

    fun randomPicture(): String {
        var p = (Math.random() * 4).toInt() + 1
        return p.toString()
    }
}