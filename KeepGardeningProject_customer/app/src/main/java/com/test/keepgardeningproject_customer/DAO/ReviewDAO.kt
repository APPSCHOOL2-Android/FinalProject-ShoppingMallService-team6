package com.test.keepgardeningproject_customer.DAO
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

// 리뷰 목록 표시를 위한 클래스
data class ReviewList (
    val reviewIdx: Long,
    val productImage: String,
    val storeName: String,
    val productName: String,
    val reviewTitle: String,
    val reviewRating: Long
)