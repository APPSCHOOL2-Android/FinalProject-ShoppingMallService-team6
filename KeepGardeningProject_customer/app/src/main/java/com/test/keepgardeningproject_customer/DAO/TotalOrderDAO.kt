package com.test.keepgardeningproject_customer.DAO

data class TotalOrderClass (
    var totalOrderIdx: Long,
    var totalOrderCustomerIdx: Long,
    var totalOrderDate: String,
    var totalOrderPrice: Long,
    var totalOrderOrdererPhone: String,
    var totalOrderReceiverName: String,
    var totalOrderReceiverPhone: String,
    var totalOrderReceiverAddress: String,
    var totalOrderReceiverAddressDetail: String,
    var totalOrderDeliveryRequest: String?,
    var TotalOrderPayment: String
)