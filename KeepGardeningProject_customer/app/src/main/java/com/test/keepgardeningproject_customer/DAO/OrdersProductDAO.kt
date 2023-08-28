package com.test.keepgardeningproject_customer.DAO

class OrdersProductDAO {
}

data class OrdersProductClass (
    var ordersIdx: Long,
    var ordersCustomerIdx: Long,
    var ordersProductIdx: Long,
    var ordersStoreRequest: String?,
    var ordersProductCount: Long,
    var ordersProductPrice: Long,
    var ordersDeliveryState: String,
    var ordersTotalOrderIdx: Long
)