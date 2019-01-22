package com.shang.fcu_food.Data

import com.google.firebase.auth.FirebaseUser

class User {
    var uid:String=""
    var name:String=""
    var picture:String="1"
    var sex:String="man"


    constructor()

    override fun toString(): String {
        return "$name $sex $uid $picture"
    }

    fun getUser(firebaseUser:FirebaseUser):User{

        name=firebaseUser.displayName!!
        uid=firebaseUser.uid
        sex=sex
        picture=randomPicture()

        return this
    }

    fun toMap():MutableMap<String,Any>{
        var map= mutableMapOf<String,Any>()
        map.put("name",name)
        map.put("picture",picture)
        map.put("sex",sex)
        return map
    }

    fun randomPicture():String{
        var p=(Math.random()*2).toInt()+1
        return p.toString()
    }
}