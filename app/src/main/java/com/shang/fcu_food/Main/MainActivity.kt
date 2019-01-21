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
import com.shang.fcu_food.*
import com.shang.fcu_food.Dialog.AddMenuDialog
import com.shang.fcu_food.Dialog.AddShopDialog
import com.shang.fcu_food.Dialog.NetworkDialog
import com.shang.fcu_food.R
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.alert
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
                    FirebaseUnits.auth_Login(this)

                R.id.menu_logout ->
                    AuthUI.getInstance().signOut(this).addOnCompleteListener {
                        toast("登出成功")
                        finish()
                    }

                R.id.menu_question->
                    AddShopDialog.getInstance().show(supportFragmentManager,AddShopDialog.TAG)

                    //alert(R.string.Menu_Question_Message,R.string.Menu_Question_Title).show()
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
                    FirebaseUnits.database_BindAllUser()  //取得所有使用者的資訊
                    var adapter = ViewPagerAdapter(
                        supportFragmentManager,
                        resources.getStringArray(R.array.ShopType)
                    )
                    viewPager.adapter = adapter
                    slidingTab.setViewPager(viewPager)
                }else{
                    FirebaseUnits.auth_Login(this)
                }
            } else {
                NetworkDialog.getInstance(handler)
                    .show(supportFragmentManager, NetworkDialog.TAG)
            }
        }
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
