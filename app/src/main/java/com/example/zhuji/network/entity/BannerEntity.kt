package com.example.zhuji.network.entity

data class BannerEntity(
    var id:Int,
    var desc:String,
    var imagePath:String,
    var isVisible:Int,
    var order:Int,
    var title:String,
    var type:Int,
    var url:String
)
