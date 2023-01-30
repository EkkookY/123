package com.example.zhuji

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.toArgb
import com.example.zhuji.base.BaseActivity
import com.example.zhuji.ui.common.MainScaffoldConfig
import com.example.zhuji.ui.theme.AppTheme
import com.example.zhuji.ui.theme.themeState
import com.example.zhuji.utils.DialogUtil
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class,
        ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialogUtil.initDialog(this)
            AppTheme(themeKey = themeState.value) {
                //根据主题颜色设置状态栏背景颜色
                setStatusBarColor(MaterialTheme.colors.primary.toArgb())
                MainScaffoldConfig()
            }
        }
    }
}