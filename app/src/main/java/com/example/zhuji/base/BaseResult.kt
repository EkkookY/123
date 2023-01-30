package com.example.zhuji.base

data class BaseResult<T>(
    var errorCode: Int = -1,
    var errorMsg: String = "",
    val data: T?
)

data class BasePage<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over:Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)

enum class ResultCodeEnum(private val code: Int, private val message:String){

    /**
     * 响应成功
     */
    SUCCESS(0,"响应成功"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002,"net error");



    fun getCode(): Int {
        return code
    }

    fun getMessage():String{
        return message
    }
}
