package com.test.keepgardeningproject_customer.DAO

class CartDAO {
}

data class CartClass(
    var cartIdx: Long,
    var cartUserIdx: Long,
    var cartProductIdx: MutableList<Long>,
    var cartCount: Long
)