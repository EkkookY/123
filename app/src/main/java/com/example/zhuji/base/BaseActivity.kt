package com.example.zhuji.base

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

/**
 * activity基类
 * */
abstract class BaseActivity : ComponentActivity(){

    private var exitTime = 0L
    /**
     * 在onCreate方法里面拦截统一返回
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    val msg = "再按一次退出程序"
                    Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
                } else {
                    moveTaskToBack(true)
                }
            }
        })
    }


    /**
     * 设置状态栏颜色
     * */
    fun setStatusBarColor(color:Int){
        val window: Window = this.window
        window.statusBarColor = color
    }
}