package com.shang.fcu_food.Dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.shang.fcu_food.R

class MySpinnerAdapter(var context: Context) : BaseAdapter() {

    var title: Array<String> = arrayOf()
    var image: Array<Int> = arrayOf()

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