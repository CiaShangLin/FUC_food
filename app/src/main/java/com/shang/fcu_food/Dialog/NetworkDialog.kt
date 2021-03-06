package com.shang.fcu_food.Dialog

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.shang.fcu_food.R


class NetworkDialog : DialogFragment() {

    companion object {
        val NETWORK_STATUS: Int = 400
        val TAG = "NetworkDialog"

        private lateinit var handler: Handler
        private var networkDialog: NetworkDialog? = null

        fun getInstance(handler: Handler): NetworkDialog {
            if (networkDialog == null) {
                networkDialog = NetworkDialog()
            }
            Companion.handler = handler
            return networkDialog!!
        }

        fun checkNetworkStatus(context: Context): Boolean {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val mNetworkInfo = mConnectivityManager?.activeNetworkInfo

            if(mNetworkInfo!=null && mNetworkInfo.isConnected){
                return true
            }

            Log.v(TAG, "網路狀態:" + mNetworkInfo?.isConnected)
            return false

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable=false

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_network, container, false)
        var networkBt = view.findViewById<Button>(R.id.networkBt)
        networkBt.setOnClickListener {
            handler.sendMessage(Message().apply {
                this.what = NETWORK_STATUS
            })
            dismiss()
        }
        return view
    }


}