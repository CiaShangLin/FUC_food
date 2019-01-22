package com.shang.fcu_food.Unit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

class PickPictureUnit {

    companion object {
        val REQUEST_CODE:Int=1

        fun pickIntent():Intent{
            return Intent().apply {
                this.setType("image/*")
                this.action = Intent.ACTION_GET_CONTENT
            }
        }
        fun uriToBitmap(activity: Activity,data: Intent?): Bitmap {
            var uri = data?.data
            return MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), uri)
        }

        fun bitmapToByte(bitmap: Bitmap?): ByteArray {
            var byteArrayOutputStream = ByteArrayOutputStream()
            if (bitmap == null) {
                return byteArrayOf()
            } else {
                //有可能是長得或是橫的
                var width = if (bitmap.width > bitmap.height) 720 else 480
                var height = if (bitmap.height > bitmap.width) 720 else 480
                var tempBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            }
            return byteArrayOutputStream.toByteArray()
        }
    }
}