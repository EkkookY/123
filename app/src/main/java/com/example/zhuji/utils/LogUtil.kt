package com.example.zhuji.utils

import android.util.Log
import com.example.zhuji.BuildConfig

object LogUtil {
    private const val TAG = "requestTAG"
    private const val TAG_NET = "requestTAG"

    fun i(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG, message.toString())
    }

    fun e(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG, message.toString())
    }

    fun showHttpHeaderLog(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG_NET, message.toString())
    }

    fun showHttpApiLog(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG_NET, message.toString())
    }

    fun showHttpLog(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG_NET, message.toString())
    }
}