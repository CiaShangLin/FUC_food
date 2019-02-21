package com.shang.fcu_food.Dialog

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits
import kotlinx.android.synthetic.main.dialog_user_setting.*
import org.jetbrains.anko.support.v4.toast

class UserSettingDialog : DialogFragment() {

    companion object {
        val TAG = "UserSettingDialog"
        private var userSettingDialog: UserSettingDialog? = null
        fun getInstance(): UserSettingDialog {
            if (userSettingDialog == null) {
                userSettingDialog = UserSettingDialog()
            }
            return userSettingDialog as UserSettingDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_user_setting, container, false)
        var userSettingNameTvEt = view.findViewById<TextInputLayout>(R.id.userSettingNameTvEt)
        var userSettingGenderRg = view.findViewById<RadioGroup>(R.id.userSettingGenderRg)
        var userSettingUpdateBt = view.findViewById<Button>(R.id.userSettingUpdateBt)
        var userSettingPictureSp = view.findViewById<Spinner>(R.id.userSettingPictureSp).apply {
            this.adapter=MySpinnerAdapter(context)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user = FirebaseUnits.auth_uidToUser()
        defaultSetting(user!!)

        userSettingUpdateBt.setOnClickListener {
            var name = userSettingNameTvEt.editText?.text.toString()
            var gender = when (userSettingGenderRg.checkedRadioButtonId) {
                R.id.userSettingManRb -> "man"
                R.id.userSettingWomanRb -> "woman"
                else -> ""
            }
            var picture = (userSettingPictureSp.selectedItemPosition + 1).toString()
            var updateUser = User(user.uid, name, picture, gender)

            FirebaseUnits.database_updateUser(updateUser)
            toast("更新成功")
            dismiss()
        }

    }

    private fun defaultSetting(user:User){
        //預設名字
        userSettingNameTvEt.editText?.setText(user.name)

        //預設性別
        if (user.sex.equals("man")) {
            userSettingManRb.isChecked = true
        } else {
            userSettingWomanRb.isChecked = true
        }

        //原先頭像
        userSettingPictureSp.setSelection(user.picture.toInt() - 1)
    }


    inner class MySpinnerAdapter(var context: Context) : BaseAdapter() {

        private var title: Array<String> = arrayOf()
        private var image: Array<Int> = arrayOf()

        init {
            title = context.resources.getStringArray(R.array.userSetting_Title)
            image = arrayOf<Int>(R.drawable.ic_cat, R.drawable.ic_dog, R.drawable.ic_boy, R.drawable.ic_girl)
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return title.size
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = LayoutInflater.from(context).inflate(R.layout.spinner_user_setting, parent, false)
            var name = view.findViewById<TextView>(R.id.userSettingNameSpTv)
            var picture = view.findViewById<ImageView>(R.id.userSettingPictureSpImg)

            name.setText(title[position])
            picture.setImageResource(image[position])
            return view
        }


    }

}

