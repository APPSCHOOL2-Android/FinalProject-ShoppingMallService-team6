package com.test.keepgardeningproject_seller.DAO
data class QnAClass(
    var QnAIdx: Long,
    var QnAProductType: String,
    var QnAProductIdx: Long,
    var QnACustomerIdx: Long,
    var QnAStoreIdx: Long,
    var QnATitle: String,
    var QnAContent: String,
    var QnAAnswer: String,
    var QnADate: String
)

data class qnaInfo(
    var qnaidx:Long,
    var qnaAnswer:String,
    var qnaimg :String,
    var userName:String,
    var productname:String,
    var qnaTitle:String,
    var productIdx:Long
)
