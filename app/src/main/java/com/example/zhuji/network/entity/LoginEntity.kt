package com.example.zhuji.network.entity

data class LoginEntity(
    var admin:Boolean = false,
    var chapterTops:Any? = null,
    var coinCount:Int = -1,
    var collectIds:Any? = null,
    var email:String? = null,
    var icon:String? = null,
    var id:Int = -1,
    var nickname:String? = null,
    var password:String? = null,
    var publicName:String? = null,
    var token:String? = null,
    var type:Int = -1,
    var username:String? = null
)

