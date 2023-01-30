package com.example.zhuji

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zhuji.network.RetrofitClient.retrofit
import com.example.zhuji.network.entity.ArticleEntity

class HomeArticleDataSource : PagingSource<Int, ArticleEntity>() {
    companion object{
        const val pageSize = 10 //每次加载的条数
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        return try {
            val currentPage = params.key ?: 0  //获取当前的页数,若为空,默认值为 1

            var dataList = retrofit.getArticleList(page = currentPage, pageSize)   //请求数据

            //上一页的key
            val prevKey: Int? = (currentPage - 1).run {
                if (this <= 0) {//说明当前是第一页数据
                    null
                } else {
                    this
                }
            }

            //下一页的key
            val nextKey: Int? = when {
                params.loadSize > pageSize -> {//第一次加载的会多一些
                    // public val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER,
                    // 默认PagingConfig为pager分配初始获取数据的大小为pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
                    // 所以Pager配置时，如果initialLoadSize不指定，那么第一次加载数据并不是我们定义的pageSize
                    val count = params.loadSize / pageSize
                    count + 1
                }

                !dataList.data!!.over -> {//dataList.data.over 是否加载完毕，如果没有则可以进行下一页，当前页码+1
                    currentPage + 1
                }
                else -> {
                    null
                }
            }

            //返回结果
            LoadResult.Page(dataList.data!!.datas, prevKey, nextKey)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}