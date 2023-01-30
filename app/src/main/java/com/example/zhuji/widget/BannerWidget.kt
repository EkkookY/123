package com.example.zhuji.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.zhuji.network.entity.BannerEntity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

/**
 * @param dataList 数据列表
 * @param bannerHeight banner高度
 * @param auto 是否自动播放
 * @param autoTime 轮播时间
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun BannerWidget(
    dataList: List<BannerEntity>,
    bannerHeight: Dp = 150.dp,
    auto:Boolean = true,
    autoTime:Long = 2000L) {

    val startIndex = 0
    val pagerState = rememberPagerState(initialPage = startIndex)

    var currentTime by remember {
        mutableStateOf(10L)
    }

    if (auto) {
        LaunchedEffect(key1 = currentTime) {
            delay(autoTime)
            if (pagerState.currentPage == dataList.size - 1) {
                pagerState.animateScrollToPage(0)
            } else {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
            currentTime = System.currentTimeMillis()
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(bannerHeight)) {
        HorizontalPager(
            count = dataList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Image(painter = rememberImagePainter(data = dataList[index].imagePath),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}