package com.shang.fcu_food.DetailShop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.shang.fcu_food.Data.*
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.activity_detail_shop.*
import org.jetbrains.anko.toast


class DetailShopActivity : AppCompatActivity() {

    var position: Int = 0
    var shop_tag: String = ""
    lateinit var adapter: Any
    lateinit var options: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_shop)

        position = intent.extras.getInt(DataConstant.POSITION)
        shop_tag = intent.extras.getString(DataConstant.SHOP_TYPE_TAG)
        Log.v("TAG", "$position $shop_tag")

        detailShopTb.inflateMenu(R.menu.menu_detailshop)
        detailShopTb.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_search->toast("SEARCH")
                R.id.menu_addShop->toast("ADD SHOP")
            }
            true
        }

        var query = FirebaseDatabase.getInstance().getReference().child(shop_tag)
        when (shop_tag) {
            BreakfastShop.tag ->
                options = FirebaseRecyclerOptions.Builder<BreakfastShop>().setQuery(query, BreakfastShop::class.java).build()
            DinnerShop.tag ->
                options = FirebaseRecyclerOptions.Builder<DinnerShop>().setQuery(query, DinnerShop::class.java).build()
            SnackShop.tag ->
                options = FirebaseRecyclerOptions.Builder<SnackShop>().setQuery(query, SnackShop::class.java).build()
            DrinkShop.tag ->
                options = FirebaseRecyclerOptions.Builder<DrinkShop>().setQuery(query, DrinkShop::class.java).build()
        }

        adapter = object : FirebaseRecyclerAdapter<Shop, DetailShopVH>(options as FirebaseRecyclerOptions<Shop>) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailShopVH {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cardview_detailshop, parent, false)
                return DetailShopVH(view)
            }

            override fun onBindViewHolder(holder: DetailShopVH, position: Int, model: Shop) {
                holder.bind(shop_tag,model)
            }
        }

        detailShopRecyc.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailShopRecyc.adapter = adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>

        var pagerSnapHelper=PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(detailShopRecyc)
    }

    override fun onResume() {
        super.onResume()
        detailShopRecyc.smoothScrollToPosition(position)
    }

    override fun onStart() {
        super.onStart()
        (adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>).startListening()
    }

    override fun onStop() {
        super.onStop()
        (adapter as FirebaseRecyclerAdapter<Shop, DetailShopVH>).stopListening()
    }
}

