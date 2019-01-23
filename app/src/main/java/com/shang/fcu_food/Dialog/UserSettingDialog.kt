package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.shang.fcu_food.Data.User
import com.shang.fcu_food.R
import com.shang.fcu_food.Unit.FirebaseUnits
import kotlinx.android.synthetic.main.dialog_user_setting.*
import org.jetbrains.anko.support.v4.toast

class UserSettingDialog :DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_user_setting,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userSettingNameTvEt=view.findViewById<TextInputLayout>(R.id.userSettingNameTvEt)
        var userSettingGenderRg=view.findViewById<RadioGroup>(R.id.userSettingGenderRg)
        var userSettingPhotoRg=view.findViewById<RadioGroup>(R.id.userSettingPhotoRg)
        var userSettingUpdateBt=view.findViewById<Button>(R.id.userSettingUpdateBt)

        var user=FirebaseUnits.auth_uidToUser()
        defaultName(user!!.name)
        defaultGender(user!!.sex)
        defaultPicture(user!!.picture)
        
        userSettingUpdateBt.setOnClickListener {
            var name=userSettingNameTvEt.editText?.text.toString()
            var gender=when(userSettingGenderRg.checkedRadioButtonId){
                R.id.userSettingManRb->"man"
                R.id.userSettingWomanRb->"woman"
                else -> ""
            }
            var picture=when(userSettingPhotoRg.checkedRadioButtonId){
                R.id.userSettingCatRb->"1"
                R.id.userSettingDogRb->"2"
                else -> "1"
            }

            var updateUser= User(user.uid,name,picture,gender)
            FirebaseUnits.database_updateUser(updateUser)
            toast("設定成功")
            dismiss()
        }
    }

    fun defaultName(name:String?){
        userSettingNameTvEt.editText?.setText(name.toString())
    }

    fun defaultGender(gender:String){
        if(gender.equals("man")){
            userSettingManRb.isChecked=true
        }else{
            userSettingWomanRb.isChecked=true
        }
    }

    fun defaultPicture(picture:String){
        if(picture.equals("1")){
            userSettingCatRb.isChecked=true
        }else{
            userSettingDogRb.isChecked=true
        }
    }

    override fun onResume() {
        super.onResume()
    }
}