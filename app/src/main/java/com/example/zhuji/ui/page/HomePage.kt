package com.example.zhuji.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.zhuji.network.entity.ArticleEntity
import com.example.zhuji.ui.common.AppTopBar
import com.example.zhuji.ui.common.ArticleComponents
import com.example.zhuji.viewmodel.HomeViewEvent
import com.example.zhuji.viewmodel.HomeViewModel
import com.example.zhuji.widget.BannerWidget
import com.example.zhuji.widget.RefreshWidget

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(navHostController: NavHostController,viewModel: HomeViewModel = hiltViewModel()){

    val viewState = viewModel.viewState
    val bannerList = viewState.bannerList

    val articleDataList = viewState.pagingData?.collectAsLazyPagingItems()

    DisposableEffect(Unit){
        viewModel.dispatch(HomeViewEvent.onStartEvent)
        onDispose {  }
    }

    Scaffold(
        topBar = {
            AppTopBar(title = "首页", showArrowBack = false, onLeftIconClickListener = {})
        }
    ) {
        Column {
            bannerList.let {
                BannerWidget(dataList = bannerList)
            }

            articleDataList?.let {
                RefreshWidget(lazyPagingItems = articleDataList){
                    itemsIndexed(articleDataList){ index: Int, value: ArticleEntity? ->
                        ArticleComponents(
                            articleEntity = value!!,
                            onItemClick = {

                            },
                            onCollectionClick = {
                                viewModel.dispatch(HomeViewEvent.onCollectionEvent, collectionId = it)
                            }
                        )
                    }
                }
                if (viewState.isNeedReload){
                    //重绘页面？
                }
            }

        }
    }

}