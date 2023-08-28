package com.test.keepgardeningproject_customer.DAO

class TotalOrderDAO {
}

data class TotalOrderClass (
    var totalOrderIdx: Long,
    var totalOrderCustomerIdx: Long,
    //var totalOrderIdxList: ArrayList<Long>,
    var totalOrderDate: String,
    //var totalOrderDeliveryIdx: Long,
    var totalOrderPrice: Long,
    var totalOrderOrdererPhone: String,
    var totalOrderReceiverName: String,
    var totalOrderReceiverPhone: String,
    var totalOrderReceiverAddress: String,
    var totalOrderReceiverAddressDetail: String,
    var totalOrderDeliveryRequest: String?,
    var TotalOrderPayment: String
)