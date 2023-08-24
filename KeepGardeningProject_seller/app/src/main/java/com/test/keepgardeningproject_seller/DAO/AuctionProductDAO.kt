package com.test.keepgardeningproject_seller.DAO

class AuctionProductDAO {
}

data class AuctionProductClass(
    var auctionProductIdx: Long,
    var auctionProductImageList: ArrayList<String>,
    var auctionProductName: String,
    var auctionProductOpenPrice: String,
    var auctionProductStoreIdx: Long,
    var auctionProductOpenDate: String,
    var auctionProductCloseDate: String,
    var auctionProductDetail: String
)