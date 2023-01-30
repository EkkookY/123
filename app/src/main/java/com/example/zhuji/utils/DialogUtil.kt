package com.example.zhuji.utils

import android.annotation.SuppressLint
import android.app.Activity
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

@SuppressLint("StaticFieldLeak")
private var dialogContext: Activity? = null

object DialogUtil {

    fun initDialog(context: Activity){
        dialogContext = context
    }

    @SuppressLint("StaticFieldLeak")
    private var loadingDialog:LoadingPopupView? = null

    /**
     * 显示加载弹窗
     * */
    fun showLoadingDialog(title: String? = null){
        if (loadingDialog == null)
            loadingDialog = XPopup.Builder(dialogContext)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asLoading()

        loadingDialog?.setTitle(title)?.show()
    }

    /**
     * 关闭加载弹窗
     * */
    fun closeLoadingDialog(){
        if (loadingDialog != null)
            loadingDialog?.dismiss()
    }
}