package com.shang.fcu_food

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.shang.fcu_food.Data.TempMenu
import org.jetbrains.anko.toast

class UploadAsy(var context: Context, var ref_path: String, var tempMenu: Any, var imageByte: ByteArray) :
    AsyncTask<String, Int, Boolean>() {

    var progressDialog = ProgressDialog(context).apply {
        this.max = imageByte.size
        this.setCancelable(false)
        this.setTitle("上傳中...")
        this.setMessage("努力上傳中")
    }

    override fun onPreExecute() {
        super.onPreExecute()

        progressDialog.show()
    }

    override fun doInBackground(vararg p0: String?): Boolean {
        var addDatabaseStatus=false
        var addStorageStatus=false

        //FirebaseUnits.storage_addMenuImage(imageByte,ref_path)

        Log.d("TAG",addDatabaseStatus.toString())

        return true
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        progressDialog.progress = values[0]!!
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)

        context.toast("新增成功")

        progressDialog.dismiss()

    }
}