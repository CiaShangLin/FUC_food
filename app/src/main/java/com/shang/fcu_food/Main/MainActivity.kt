package com.shang.fcu_food.Main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import android.support.v7.app.ActionBarDrawerToggle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.shang.fcu_food.FirebaseUnits
import com.shang.fcu_food.NetworkDialog
import com.shang.fcu_food.PermissionUnit
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                NetworkDialog.NETWORK_STATUS -> init()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //bbbb.setRippleColorResource(R.color.colorAccent)
        }
        /*lottie.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                lottie.visibility=View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })*/

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_navigation)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_user_setting ->
                    FirebaseUnits.database_addShop()
                    //FirebaseUnits.auth(this)

                R.id.menu_logout ->
                    AuthUI.getInstance().signOut(this).addOnCompleteListener {
                        toast("登出成功")
                    }
            }
            drawer_layout.closeDrawers()
            true
        }

        init()
    }

    fun init() {
        if (!PermissionUnit.checkPermission(this)) {        //已通過權限
            if (NetworkDialog.checkNetworkStatus(this)) {   //網路已開啟
                if(FirebaseUnits.checkHasAuth()){
                    var adapter = ViewPagerAdapter(
                        supportFragmentManager,
                        resources.getStringArray(R.array.ShopType)
                    )
                    viewPager.adapter = adapter
                    slidingTab.setViewPager(viewPager)
                }else{
                    FirebaseUnits.auth(this)
                }
            } else {
                NetworkDialog.getInstance(handler)
                    .show(supportFragmentManager, NetworkDialog.TAG)
            }
        }
    }


    fun realtimeDatabase() {
        //database.getReference("breakfast") 一開始就是fucfood了
        //上傳資料用update 就算沒有那個節點他也會自己幫你生出來 ex:breakfast/3
        //setValue會把你整個節點都改掉
        //push他會自己生出一個hashID這個不是我要用的
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("breakfast/4")
        var map = mutableMapOf<String, Any>()
        map.put("店名", "EEE")
        map.put("地址", "EEE")
        myRef.updateChildren(map)

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.d("TAG Change", p1 + " " + p0.key + " " + p0.value)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                toast("新增成功")
            }
        })


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (d in dataSnapshot.children) {
                    //Log.d("TAG", "Value is: " + d.value+" key:"+d.key)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    fun storage() {
        var storageRef = FirebaseStorage.getInstance().getReference()
        var riversRef = storageRef.child("image/surf369.jpeg")

        riversRef.downloadUrl.addOnSuccessListener {
            Log.d("TAG", it.path)

        }.addOnCompleteListener {

        }.addOnFailureListener {
            it.printStackTrace()
        }

        //GlideApp.with(this).load(storageRef.child("image/surf369.jpeg")).into(imageView2)

    }

    fun update() {
        var storageRef = FirebaseStorage.getInstance().getReference()
        val file = Uri.fromFile(
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .path + "/surf369.jpeg"
            )
        )


        Log.d("TAG", file.path)
        val riversRef = storageRef.child("image/surf369.jpeg")
        riversRef.putFile(file)
            .addOnSuccessListener { taskSnapshot ->
                // Get a URL to the uploaded content
                taskSnapshot.uploadSessionUri!!.path
                toast("SUCCESS")
            }
            .addOnFailureListener {
                // Handle unsuccessful uploads
                // ...
                toast("Fail")
                it.printStackTrace()
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!PermissionUnit.checkPermission(this)) {
            init()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FirebaseUnits.AUTH_RQEUESTCODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                init()
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("TAG", user?.email + " " + user?.uid+" "+user?.displayName)
            } else {
                Log.d("TAG", "FALL")
            }
        }
    }
}
