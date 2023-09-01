package com.test.keepgardeningproject_seller.DAO

data class AuctionClass(
    var auctionIdx: Long,
    var auctionProductIdx: Long,
    var auctionBidNickname: String,
    var auctionBidPrice: String,
    var auctionCustomerIdx: Long
)