package com.example.zhuji.network

import com.example.zhuji.base.BasePage
import com.example.zhuji.base.BaseResult
import com.example.zhuji.network.entity.ArticleEntity
import com.example.zhuji.network.entity.BannerEntity
import com.example.zhuji.network.entity.LoginEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val Base_Url = "https://www.wanandroid.com"
    }

    /**
     * 获取首页banner
     * */
    @GET("banner/json")
    suspend fun getBanner(): BaseResult<MutableList<BannerEntity>>

    /**
     * 获取文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("page_size") pageSize:Int
    ): BaseResult<BasePage<ArticleEntity>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String): BaseResult<LoginEntity>

    @GET("/user/logout/json")
    suspend fun loginOut(): BaseResult<Any>

//    @POST("/user/lg/userinfo/json")
//    suspend fun getUserConfig():BaseResult<UserConfigEntity>
//
//    @GET("/lg/collect/list/{page}/json")
//    suspend fun collectionList(
//        @Path("page") page: Int,
//        @Query("page_size") pageSize:Int
//    ):BaseResult<BasePage<CollectionEntity>>

    @POST("/lg/collect/{id}/json")
    suspend fun collectionArticle(
        @Path("id") id:Int
    ):BaseResult<Any>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectionArticle(
        @Path("id") id:Int
    ):BaseResult<Any>

//    @GET("https://www.wanandroid.com//lg/coin/list/{page}/json")
//    suspend fun integralList(
//        @Path("page") page:Int
//    ):BaseResult<BasePage<IntegralEntity>>

}