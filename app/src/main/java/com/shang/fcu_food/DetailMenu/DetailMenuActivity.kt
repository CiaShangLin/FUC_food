package com.shang.fcu_food.DetailMenu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.DataConstant
import com.shang.fcu_food.Data.DataConstant.Companion.SHOP_TYPE_TAG
import com.shang.fcu_food.Data.Menu
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_detail_menu.*

class DetailMenuActivity : AppCompatActivity() {

    //店家類型TAG 店家的KEY 點選位置POSITION
    //update SHOP_TYPE_TAG+SHOP_ID+"menu"+menu_id+usercomment+"comment_size"
    companion object {
        val TAG="DetailMenuActivity"

        var shop_type_tag: String = ""
        var shop_id: String = ""
        var position: Int = 0
        var shop_name:String=""
    }

    lateinit var adapter: Any
    lateinit var options: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        var intent = intent.extras
        if (intent != null) {
            shop_name=intent.getString(DataConstant.SHOP_NAME)
            shop_type_tag = intent.getString(DataConstant.SHOP_TYPE_TAG)
            shop_id = intent.getString(DataConstant.SHOP_ID)
            position = intent.getInt(DataConstant.POSITION)
        }

        detailMenuTb.inflateMenu(R.menu.menu_detailmenu)
        detailMenuTb.setNavigationIcon(R.drawable.ic_arrow_back)
        detailMenuTb.setNavigationOnClickListener {
            finish()
        }
        detailMenuTb.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_addMenu->AddMenuDialog.getInstance(shop_name).show(supportFragmentManager,AddMenuDialog.TAG)
            }
            true
        }

        var query = FirebaseDatabase.getInstance().getReference().child("$shop_type_tag/$shop_id/menu")
        options = FirebaseRecyclerOptions.Builder<Menu>().setQuery(query, Menu::class.java).build()
        adapter = object : FirebaseRecyclerAdapter<Menu, DetailMenuVH>(options as FirebaseRecyclerOptions<Menu>) {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DetailMenuVH {
                var view = LayoutInflater.from(p0.context).inflate(R.layout.cardview_detailmenu, p0, false)
                return DetailMenuVH(view)
            }

            override fun onBindViewHolder(holder: DetailMenuVH, position: Int, model: Menu) {
                holder.bind(position, model,this@DetailMenuActivity)
            }
        }

        var layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailMenuRecyc.layoutManager = layoutManager
        detailMenuRecyc.adapter = adapter as FirebaseRecyclerAdapter<Menu, DetailMenuVH>

        var pagerSnapHelper= PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailMenuRecyc)
    }

    override fun onResume() {
        super.onResume()
        detailMenuRecyc.smoothScrollToPosition(position)
    }

    override fun onStart() {
        super.onStart()
        (adapter as FirebaseRecyclerAdapter<Menu, DetailMenuVH>).startListening()

    }

    override fun onStop() {
        super.onStop()
        (adapter as FirebaseRecyclerAdapter<Menu, DetailMenuVH>).stopListening()
    }

}
