package com.test.keepgardeningproject_customer.DAO

class PurchaseDAO {
}

data class purchaseInfo(


    //orderproduct, orderidx
    var orderproductIdx:Long,
    var ordersIdx:Long,

    //purchaseIdx == userIdx
    var purchaseIdx: Long,

    //orderTotalOrder
    var totalorderIdx:Long,

    var purchaseInfoIdx:Long,

    var purchaseTitle: String,
    var purchaseImg: String,
    var purchaseState: String
)


