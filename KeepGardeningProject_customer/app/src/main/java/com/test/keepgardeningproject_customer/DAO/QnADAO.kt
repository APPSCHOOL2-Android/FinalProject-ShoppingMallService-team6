package com.test.keepgardeningproject_customer.DAO

class QnADAO {
}

data class QnAClass(
    var QnAIdx: Long,
    var QnAProductType: String,
    var QnAProductIdx: Long,
    var QnACustomerIdx: Long,
    var QnAStoreIdx: Long,
    var QnATitle: String,
    var QnAContent: String,
    var QnAAnswer: String,
    var QnADate: String,
    var QnAImageList: ArrayList<String>
)

data class QnaInfo(
    var qnaIdx :Long,
    var qnaImgList :String,
    var qnaStoreName:String,
    var qnaName:String,
    var qnaDetail:String,
    var qnaProductIdx:Long,
    var qnaStoreIdx:Long,
    var qnaAnswer:String

)