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
    var QnADate: String
)