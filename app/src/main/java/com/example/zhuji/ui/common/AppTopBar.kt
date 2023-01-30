package com.example.zhuji.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * @param title 标题,必传
 * @param searchContent 搜索内容,这里是一个Composable,需要自己写搜索样式
 * @param leftIcon 左边的icon,矢量图
 * @param showArrowBack 是否显示左边的矢量图
 * @param rightContent 右边的内容,这里是一个Composable,需要自己写样式
 */
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier
        .height(AppBarHeight)
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primary),
    title:String,
    searchContent:@Composable (()->Unit) ?= null,
    leftIcon: ImageVector = Icons.Default.ArrowBack,
    onLeftIconClickListener:()->Unit,
    showArrowBack:Boolean = true,
    rightContent:@Composable (()->Unit) ?= null
){
    ConstraintLayout(modifier = modifier) {
        val (titleView,searchView,leftView,rightView) = createRefs()

        if (showArrowBack){
            Icon(imageVector = leftIcon,
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onLeftIconClickListener.invoke()
                    }
                    .constrainAs(leftView) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start, 12.dp)
                    }
            )
        }

        if (searchContent != null){
            Row(modifier = Modifier.constrainAs(searchView){
                centerTo(parent)
            }){
                searchContent()
            }
        }else{
            Text(text = title,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(titleView){
                    centerTo(parent)
                }
            )
        }

        if (rightContent != null){
            Row(modifier = Modifier.constrainAs(rightView){
                centerVerticallyTo(parent)
                end.linkTo(parent.end, margin = 12.dp)
            }) {
                rightContent()
            }
        }
    }
}

private val AppBarHeight = 40.dp