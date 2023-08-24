package com.test.keepgardeningproject_customer.DAO

class CartDAO {
}

data class CartClass(
    var cartIdx: Long,
    var cartUserIdx: Long,
    var cartProductIdx: Long,
    var cartName: String,
    var cartPrice: Long,
    var cartCount: Long
)
