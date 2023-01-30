package com.example.zhuji.ui.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.zhuji.R
import com.example.zhuji.network.entity.ArticleEntity
import com.example.zhuji.ui.theme.itemStyleBlock_white_16sp
import com.example.zhuji.ui.theme.itemStyle_white_13sp
import com.example.zhuji.ui.theme.itemStyle_white_14sp
import com.google.gson.Gson

@Composable
fun ArticleComponents(articleEntity: ArticleEntity,onItemClick:(String)->Unit,onCollectionClick:(id:Int)->Unit){
    Log.e("createCollectionTAG","re load: ---:${Gson().toJson(articleEntity)}")

    Column(modifier = Modifier
        .padding(start = 16.dp, top = 10.dp, end = 16.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(10.dp))
        .background(MaterialTheme.colors.primary)
        .clickable {
            onItemClick.invoke(articleEntity.link!!)
        }
    ) {
        createArticleHeader(name = articleEntity.chapterName!!, shareTime = articleEntity.niceDate!!)

        Text(
            text = "${articleEntity.title}",
            style = itemStyleBlock_white_16sp,
            modifier = Modifier.padding(10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        createCollection(
            superChapterName = articleEntity.superChapterName!!,
            isCollected = articleEntity.collect,
            onCollectionClick = {
                onCollectionClick.invoke(articleEntity.chapterId)
            }
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun createArticleHeader(name:String,shareTime:String){
    Log.e("createCollectionTAG","re load: ---")
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (img,username,time) = createRefs()

        Image(
            painter = rememberImagePainter(data = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg"),
            contentDescription = "头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .constrainAs(img) {
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                }
        )

        Text(
            text = name,
            style = itemStyle_white_14sp,
            modifier = Modifier.constrainAs(username){
                top.linkTo(img.top,3.dp)
                start.linkTo(img.end,10.dp)
            }
        )

        Text(
            text = shareTime,
            style = itemStyle_white_13sp,
            modifier = Modifier.constrainAs(time){
                start.linkTo(img.end,10.dp)
                bottom.linkTo(img.bottom,3.dp)
            }
        )
    }
}

@Composable
private fun createCollection(superChapterName:String,isCollected:Boolean,onCollectionClick: () -> Unit){
    Log.e("createCollectionTAG","re load: ${isCollected}")
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)) {
        val (tabView,collectView) = createRefs()

        Text(
            text = superChapterName,
            style = itemStyle_white_13sp,
            modifier = Modifier.constrainAs(tabView){
                start.linkTo(parent.start,10.dp)
                centerVerticallyTo(parent)
            }
        )

        Image(
            painter = painterResource(id = if (isCollected) R.drawable.icon_star_on else R.drawable.icon_star_off),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onCollectionClick.invoke()
                }
                .constrainAs(collectView){
                    end.linkTo(parent.end,10.dp)
                    centerVerticallyTo(parent)
                }
        )
    }
}