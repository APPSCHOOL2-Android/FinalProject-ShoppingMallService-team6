package com.test.keepgardeningproject_customer.DAO

class ReviewDAO {
}

data class Review(
    val userIdx: String,
    val purchaseInfoIdx:String,
    val productIdx:Long,
    val productName:String,
    val storeName:String,
    val imageList:MutableList<String>,
    val rating:Float,
    val reviewTitle:String,
    val reviewContent:String
)
