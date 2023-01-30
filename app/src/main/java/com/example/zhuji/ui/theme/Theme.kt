package com.example.zhuji.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val themeState : MutableState<ThemeKey> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){ mutableStateOf(ThemeKey.Light) }

enum class ThemeKey{
    Light, // 白天主题
    Dark,   // 黑夜主题
    //可补充
}

@Composable
fun AppTheme(
    themeKey: ThemeKey = ThemeKey.Light,
    isDarkTheme:Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //判断系统当前主题是否是夜色主题,如果是则直接返回符合夜色主题的颜色
    val targetColors = if(isDarkTheme){
        appDarkColors()
    }else{
        //如果不是,则判断当前的key来选择符合的颜色
        when(themeKey){
            ThemeKey.Light -> appLightColors()
            ThemeKey.Dark -> appDarkColors()
            else -> appLightColors()
        }
    }

    //添加颜色动画,在主题切换的时候能平滑的过渡,而不是一下就直接切换
    //TweenSpec(500) 动画执行时间为500毫秒
    val primary = animateColorAsState(targetValue = targetColors.primary, TweenSpec(500))
    val primaryVariant = animateColorAsState(targetValue = targetColors.primaryVariant, TweenSpec(500))
    val secondary = animateColorAsState(targetValue = targetColors.secondary, TweenSpec(500))
    val secondaryVariant = animateColorAsState(targetValue = targetColors.secondaryVariant, TweenSpec(500))
    val background = animateColorAsState(targetValue = targetColors.background, TweenSpec(500))
    val surface = animateColorAsState(targetValue = targetColors.surface, TweenSpec(500))
    val error = animateColorAsState(targetValue = targetColors.error, TweenSpec(500))
    val onPrimary = animateColorAsState(targetValue = targetColors.onPrimary, TweenSpec(500))
    val onSecondary = animateColorAsState(targetValue = targetColors.onSecondary, TweenSpec(500))
    val onBackground = animateColorAsState(targetValue = targetColors.onBackground, TweenSpec(500))
    val onSurface = animateColorAsState(targetValue = targetColors.onSurface, TweenSpec(500))
    val onError = animateColorAsState(targetValue = targetColors.onError, TweenSpec(500))

    val colors: Colors = Colors(
        primary.value,
        primaryVariant.value,
        secondary.value,
        secondaryVariant.value,
        background.value,
        surface.value,
        error.value,
        onPrimary.value,
        onSecondary.value,
        onBackground.value,
        onSurface.value,
        onError.value,
        themeState.value == ThemeKey.Dark || isSystemInDarkTheme()
    )

    //将切换后的颜色替换掉系统MaterialTheme里面的colors
    androidx.compose.material.MaterialTheme(colors = colors, content = content)
}