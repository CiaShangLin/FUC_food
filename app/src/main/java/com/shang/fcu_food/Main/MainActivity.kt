package com.shang.fcu_food.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.Dialog.AddShopDialog
import com.shang.fcu_food.Dialog.NetworkDialog
import com.shang.fcu_food.Dialog.UserSettingDialog
import com.shang.fcu_food.Factory.FirebaseFactory
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.PermissionUnit
import com.shang.fcu_food.Unit.VersionCheckUnit
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                NetworkDialog.NETWORK_STATUS -> init()
                VersionCheckUnit.VERSION_CHECK_NEW -> {  //目前是新版本
                    loadView()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*lottie.addAnimatorListener(object :Animator.AnimatorListener{})*/
        toolbar.setNavigationIcon(R.drawable.ic_navigation)
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_main_addShop -> {
                    AddShopDialog.getInstance().show(supportFragmentManager, AddShopDialog.TAG)
                }
            }
            true
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_user_setting ->
                    UserSettingDialog.getInstance().show(supportFragmentManager, UserSettingDialog.TAG)
                R.id.menu_question ->
                    alert(R.string.Menu_Question_Message, R.string.Menu_Question_Title).show()
                R.id.menu_logout ->
                    AuthUI.getInstance().signOut(this).addOnCompleteListener {
                        toast("登出成功")
                        finish()
                    }
            }
            drawer_layout.closeDrawers()
            true
        }

        init()
    }

    private fun init() {
        if (!PermissionUnit.checkPermission(this)) {        //已通過權限
            if (NetworkDialog.checkNetworkStatus(this)) {   //網路已開啟
                if (FirebaseFactory.getMyFirebaseAuth().checkHasAuth()) {       //檢查是否已登入帳號
                    VersionCheckUnit.checkVersion(this, handler)  //檢查版本
                } else {
                    FirebaseFactory.getMyFirebaseAuth().auth_Login(this)
                }
            } else {
                NetworkDialog.getInstance(handler)
                    .show(supportFragmentManager, NetworkDialog.TAG)
            }
        }
    }

    private fun loadView() {
        FirebaseFactory.getMyFirebaseDatabase().database_BindAllUser()  //取得所有使用者的資訊
        viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            resources.getStringArray(R.array.ShopType)
        )
        slidingTab.setViewPager(viewPager)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!PermissionUnit.checkPermission(this)) {
            init()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FirebaseFactory.getMyFirebaseAuth().AUTH_RQEUESTCODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                init()
                if (response?.isNewUser!!) {
                    FirebaseFactory.getMyFirebaseDatabase()
                        .database_updateUser(
                            User().getUser(FirebaseFactory.getMyFirebaseAuth().auth_getUser()!!)
                            , this, R.string.usersetting_register
                        )
                } else {
                    toast("登入成功")
                }
            } else {
                toast("註冊失敗")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //AdmobUnit.getInstance(this)?.InterstitialAd_show()
    }
}
