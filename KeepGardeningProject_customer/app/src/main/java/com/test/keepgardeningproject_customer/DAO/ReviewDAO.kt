package com.test.keepgardeningproject_customer.DAO

class ReviewDAO {
}

data class Review(
    val reviewIdx: Long,
    val userIdx: String,
    val productIdx:Long,
    val productName:String,
    val storeName:String,
    val rating:Long,
    val reviewTitle:String,
    val reviewContent:String
)