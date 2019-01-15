package com.shang.fcu_food.DetailMenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shang.fcu_food.Data.Menu
import com.shang.fcu_food.DataBind
import com.shang.fcu_food.R

class DetailMenuActivity : AppCompatActivity() {

    //店家類型TAG 店家的KEY 點選位置POSITION
    //update SHOP_TYPE_TAG+SHOP_ID+"menu"+menu_id+usercomment+"comment_size"
    companion object {
        val POSITION = "POSITION"
        val SHOP_TYPE_TAG = "SHOP_TYPE_TAG"
        val SHOP_ID = "SHOP_ID"
    }

    var shop_type_tag: String = ""
    var shop_id: String = ""
    var position: Int = 0

    lateinit var adapter: Any
    lateinit var options: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        var intent = intent.extras
        if (intent != null) {
            shop_type_tag = intent.getString(SHOP_TYPE_TAG)
            shop_id = intent.getString(SHOP_ID)
            position = intent.getInt(POSITION)
        }

        Log.d("TAG","REF:"+"$shop_type_tag/$shop_id/menu")
        var query=FirebaseDatabase.getInstance().getReference().child("$shop_type_tag/$shop_id/menu")
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(data in p0.children){
                        var menu=data.value as Menu
                        Log.d("TAG",menu.name+" "+menu.price+" "+menu.usercomment)
                    }

                }
            })


    }
}
