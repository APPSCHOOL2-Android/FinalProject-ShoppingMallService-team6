package com.test.keepgardeningproject_customer.DAO
data class CartClass(
    var cartIdx: Long,
    var cartUserIdx: Long,
    var cartProductIdx: Long,
    var cartName: String,
    var cartPrice: Long,
    var cartCount: Long,
    var cartImage: String
)
