package com.shang.fcu_food

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.ProgressBar

class UploadAsy(var context: Context,var imageByte:ByteArray) : AsyncTask<String, Int, Boolean>() {

    var progressDialog=ProgressDialog(context,R.style.Widget_MaterialProgressBar_ProgressBar_Horizontal).apply {
        this.max=imageByte.size
        this.setCancelable(false)
        this.setTitle("上傳中...")
        this.setMessage("努力上傳中")
    }

    override fun onPreExecute() {
        super.onPreExecute()

        progressDialog.show()
    }

    override fun doInBackground(vararg p0: String?): Boolean {

        return true
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
    }
}