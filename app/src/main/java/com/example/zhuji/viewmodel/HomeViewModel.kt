package com.example.zhuji.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.zhuji.HomeArticleDataSource
import com.example.zhuji.base.BaseViewModel
import com.example.zhuji.network.ApiService
import com.example.zhuji.network.entity.ArticleEntity
import com.example.zhuji.network.entity.BannerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        set

    fun dispatch(event: HomeViewEvent,collectionPosition:Int = 0,collectionId:Int = 0){
        when(event){
            HomeViewEvent.onStartEvent -> {
                getBannerList()
            }
            HomeViewEvent.onCollectionEvent ->{
                //暂时没找到compose里面修改paging3数据的办法
//                viewState = viewState.copy(pagingData = viewState.pagingData?.
//                )
            }
            HomeViewEvent.onSetReLoad -> viewState = viewState.copy(isNeedReload = false)
        }
    }

    private val articleDataList = Pager(PagingConfig(pageSize = 10)){
        HomeArticleDataSource()
    }.flow.cachedIn(viewModelScope)

    private fun getBannerList(){
        launchFlow(
            requestBlock = {apiService.getBanner()},
            response = {
                if (null != it){
                    viewState = viewState.copy(
                        bannerList = it
                    )
                }
            }
        )

        viewState = viewState.copy(pagingData = articleDataList)
    }
}

data class HomeViewState(
    var bannerList: List<BannerEntity> = emptyList(),
    val articleList: List<ArticleEntity> = emptyList(),
    var pagingData: PagingCollect? = null,
    var isNeedReload: Boolean = false
)

sealed class HomeViewEvent{
    object onStartEvent : HomeViewEvent()
    object onCollectionEvent : HomeViewEvent()
    object onSetReLoad : HomeViewEvent()
}

typealias PagingCollect = Flow<PagingData<ArticleEntity>>