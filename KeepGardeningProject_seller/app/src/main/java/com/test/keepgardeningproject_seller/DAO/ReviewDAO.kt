package com.test.keepgardeningproject_seller.DAO

class ReviewDAO{
}

data class ReviewClass(
    val reviewIdx: Long,
    val userIdx: String,
    val productIdx:Long,
    val productName:String,
    val storeName:String,
    val rating:Long,
    val reviewTitle:String,
    val reviewContent:String
)