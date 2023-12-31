package com.test.keepgardeningproject_seller.DAO

data class OrdersProductClass (
    // 개별 주문번호
    var ordersIdx: Long,
    // 주문한 사용자 인덱스
    var ordersCustomerIdx: Long,
    // 주문한 상품 인덱스
    var ordersProductIdx: Long,
    var ordersProductCount: Long,
    var ordersProductPrice: Long,
    var ordersDeliveryState: String,
    // 개별 주문이 속한 총 주문의 인덱스
    var ordersTotalOrderIdx: Long
)