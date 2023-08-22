package com.test.keepgardeningproject_customer.DAO


class TestDAO {


}
data class MypageQnAData(val replyState:Boolean,val storeName:String,val productName:String,val content:MypageQnADetailData)
data class MypageQnADetailData(val imageResourceId:Int,
                               val titleQnA:String,val contentQnA:String,val
                               starNumbers:Float)

data class MypageReview(val storeName:String,val productName:String,val ratings:Float,val content: MypageReviewDetail)
data class MypageReviewDetail(val reviewImage:Int,val reviewTitle:String,val reviewContent:String)

