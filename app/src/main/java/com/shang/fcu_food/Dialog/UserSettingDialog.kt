package com.shang.fcu_food.Dialog

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.shang.fcu_food.R

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

        userSettingUpdateBt.setOnClickListener {
            var name=userSettingNameTvEt.editText?.text.toString()
            var gender=when(userSettingGenderRg.checkedRadioButtonId){
                R.id.userSettingBoyRb->"man"
                R.id.userSettingGirlRb->"girl"
                else -> ""
            }
            var picture=when(userSettingPhotoRg.checkedRadioButtonId){
                R.id.userSettingCatRb->"1"
                R.id.userSettingDogRb->"2"
                else -> "1"
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}