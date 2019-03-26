package com.shang.fcu_food.Factory

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.shang.fcu_food.Data.Temp.TempData
import com.shang.fcu_food.Data.Temp.TempMenu
import com.shang.fcu_food.Data.Temp.TempShop
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.Data.UserComment
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.Dialog.FirebaseCallback
import com.shang.fcu_food.R
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Function
import org.jetbrains.anko.toast

class MyFirebaseDatabase {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun database_BindAllUser() {
        val userRef = firebaseDatabase
            .getReference().child("user").addValueEventListener(
                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("database_BindAllUser", p0.toString())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var userList = mutableMapOf<String, User>()
                        for (data in snapshot.children) {
                            var user = data.getValue(User::class.java)
                            user?.uid = data.key!!
                            userList.put(user!!.uid, user)
                        }
                        DataBind.allUser = userList
                    }
                }
            )
    }

    //database更新評論
    //database.getReference("breakfast") 一開始就是fucfood了
    //上傳資料用update 就算沒有那個節點他也會自己幫你生出來 ex:breakfast/3
    //setValue會把你整個節點都改掉
    //push他會自己生出一個hashID這個不是我要用的
    fun database_addCommemt(
        ref_path: String,
        rating: String,
        comment: String,
        uid: String,
        activity: FragmentActivity
    ) {
        val ref = firebaseDatabase.getReference().child(ref_path)
        var map = UserComment(uid, comment, rating).toMap()
        ref.updateChildren(map).addOnSuccessListener {
            activity.toast(R.string.CommentDialog_Success)
        }.addOnFailureListener {
            activity.toast(R.string.CommentDialog_Fail)
        }
    }

    fun addTempData(tempData: TempData, imageByte: ByteArray, callback: FirebaseCallback) {
        var fileName: String = if (tempData.ref.equals("tempMenu"))
            (tempData as TempMenu).menu_name else (tempData as TempShop).shop_name

        var database = firebaseDatabase.getReference().child(tempData.ref)
        var storage = FirebaseStorage
            .getInstance().getReference(tempData.ref).child(fileName + fileName.hashCode() + ".jpg")
        var database_status = false
        var storage_status = false
        var functionMap = object : Function<List<Boolean>, Boolean> {
            override fun apply(list: List<Boolean>): Boolean? {
                if(list[0] && list[1])
                    return true
                else if(!list[0] && !list[1])
                    return false
                return null
            }
        }

        database.push().setValue(tempData).addOnSuccessListener {
            database_status = true
            callback.statusCallBack(database_status, storage_status)
            //Observable.just(listOf(database_status, storage_status)).map(functionMap).subscribe()
        }.addOnFailureListener {
            database_status = false
            callback.statusCallBack(database_status, storage_status)
            //Observable.just(listOf(database_status, storage_status)).map(functionMap).subscribe()
        }

        if (imageByte.size != 0) {  //代表有上傳照片
            var metadata = StorageMetadata.Builder().setContentDisposition("TEST").build()
            storage.putBytes(imageByte, metadata).addOnSuccessListener {
                storage_status = true
                callback.statusCallBack(database_status, storage_status)
                //Observable.just(listOf(database_status, storage_status)).map(functionMap).subscribe()
            }.addOnFailureListener {
                storage_status = false
                callback.statusCallBack(database_status, storage_status)
                //Observable.just(listOf(database_status, storage_status)).map(functionMap).subscribe()
            }
        } else {
            storage_status = true
            callback.statusCallBack(database_status, storage_status)
            //Observable.just(listOf(database_status, storage_status)).map(functionMap).subscribe()
        }
    }

    //新增和修改使用者資料
    fun database_updateUser(user: User, context: Context, message: Int) {
        var ref_user = firebaseDatabase.getReference("user").child(user.uid)
        ref_user.updateChildren(user.toMap()).addOnSuccessListener {
            context.toast(context.resources.getString(message))
        }.addOnFailureListener {
            context.toast(it.message.toString())
        }
    }

}