package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits
import kotlinx.android.synthetic.main.dialog_user_setting.*
import org.jetbrains.anko.support.v4.toast

class UserSettingDialog : DialogFragment() {

    companion object {
        val TAG = "UserSettingDialog"
        var userSettingDialog: UserSettingDialog? = null
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
            this.adapter = ArrayAdapter.createFromResource(
                context, R.array.userSetting_Picture, android.R.layout.simple_spinner_dropdown_item
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user = FirebaseUnits.auth_uidToUser()
        defaultName(user!!.name)
        defaultGender(user!!.sex)
        defaultPicture(user!!.picture)

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

    //預設名字
    fun defaultName(name: String?) {
        userSettingNameTvEt.editText?.setText(name.toString())
    }

    //預設性別
    fun defaultGender(gender: String) {
        if (gender.equals("man")) {
            userSettingManRb.isChecked = true
        } else {
            userSettingWomanRb.isChecked = true
        }
    }

    fun defaultPicture(picture: String) {
        //原先頭像
        userSettingPictureSp.setSelection(picture.toInt() - 1)
    }

}