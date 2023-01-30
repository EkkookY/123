package com.example.zhuji.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = androidx.compose.material.Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val itemStyleBlock_white_16sp = TextStyle(
    color = Color.White,
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium)

val itemStyle_white_14sp = TextStyle(
    color = Color.White,
    fontSize = 14.sp,
    fontFamily = FontFamily.Cursive)

val itemStyle_white_13sp = TextStyle(
    color = Color.White,
    fontSize = 13.sp,
    fontFamily = FontFamily.SansSerif)