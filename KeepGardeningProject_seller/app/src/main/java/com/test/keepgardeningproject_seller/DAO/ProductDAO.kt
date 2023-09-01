package com.test.keepgardeningproject_seller.DAO
data class ProductClass(
    var productIdx: Long,
    var productImageList: ArrayList<String>,
    var productName: String,
    var productPrice: String,
    var productStoreIdx: Long,
    var productCategory: String,
    var productDetail: String
)