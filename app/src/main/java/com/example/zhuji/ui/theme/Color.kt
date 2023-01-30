package com.example.zhuji.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.example.zhuji.MyApplication
import com.example.zhuji.R

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val Transparent = Color(0x00000000)

val themeColor = Color(MyApplication.mContext!!.resources.getColor(R.color.main_color))
val splashText = Color(0x25000000)
val white = Color(0xFFFFFFFF)
val white1 = Color(0xFFF7F7F7)
val white2 = Color(0xFFEDEDED)
val white3 = Color(0xFFE5E5E5)
val white4 = Color(0xFFD5D5D5)
val white5 = Color(0xFFCCCCCC)
val black = Color(0xFF000000)
val black1 = Color(0xFF1E1E1E)
val black2 = Color(0xFF111111)
val black3 = Color(0xFF191919)
val black4 = Color(0xFF252525)
val black5 = Color(0xFF2C2C2C)
val black6 = Color(0xFF07130A)
val black7 = Color(0xFF292929)
val grey1 = Color(0xFF888888)
val grey2 = Color(0xFFCCC7BF)
val grey3 = Color(0xFF767676)
val grey4 = Color(0xFFB2B2B2)
val grey5 = Color(0xFF5E5E5E)
val green1 = Color(0xEDF3EB)
val green2 = Color(0xFF67967B)
val green3 = Color(0xFF5F9378)
val red = Color(0xFFFF0000)
val red1 = Color(0xFFDF5554)
val red2 = Color(0xFFDD302E)
val red3 = Color(0xFFF77B7A)
val red4 = Color(0xFFD42220)
val red5 = Color(0xFFC51614)
val red6 = Color(0xFFF74D4B)
val red7 = Color(0xFFDC514E)
val red8 = Color(0xFFCBC7BF)
val yellow1 = Color(0xFFF6CA23)
val blue = Color(0xFF0000FF)
val info = Color(0xFF018786)
val warn = Color(0xFFD87831)

// 主题颜色自行搭配,想整好看点就慢慢搭配,好好搭配
fun appDarkColors(
    primary:Color = Color(0xFF67967B),
    primaryVariant: Color = Color(0xffa9a9a9),
    secondary: Color = Color(0xffd3d3d3),
    secondaryVariant: Color = Color(0xffd3d3d3),
    background: Color = Color(0xfff5f5f5),
    surface: Color = Color(0xFF67967B),//
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color(0xffffffff),
    onSecondary: Color = Color(0xFF67967B),//
    onBackground: Color = Color(0xfff5f5f5),
    onSurface: Color = Color(0xffffffff),
    onError: Color = Color(0xFFB00020),
    isLight: Boolean = false
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    isLight
)


fun appLightColors(
    primary:Color = Color(0xFFEDF3EB),
    primaryVariant: Color = Color(0xff272a36),
    secondary: Color = Color(0xff000000),
    secondaryVariant: Color = Color(0xff000000),
    background: Color = Color(0xffffffff),
    surface: Color = Color(0xFF232323),
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color(0xFF232323),
    onSecondary: Color = Color(0xFFD8D7D7),
    onBackground: Color = Color(0xFF232325),
    onSurface: Color = Color(0xFF232323),
    onError: Color = Color(0xFFB00020),
    isLight: Boolean = false
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    isLight
)