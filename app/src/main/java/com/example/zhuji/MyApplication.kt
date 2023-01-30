package com.example.zhuji

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.zhuji.utils.DataStoreUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        DataStoreUtils.init(this)
    }

    companion object{
        @JvmStatic
        var mContext: Context? = null
            @SuppressLint("StaticFieldLeak")
            get
    }

}