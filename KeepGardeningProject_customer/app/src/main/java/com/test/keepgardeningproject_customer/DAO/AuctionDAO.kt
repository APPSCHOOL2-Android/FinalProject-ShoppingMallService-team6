package com.test.keepgardeningproject_customer.DAO

class AuctionDAO {
}

data class AuctionInfo(
    var auctionIdx:Long,
    var auctionAuctionProductIndex:Long?,
    var auctionCustomerIdx:Long?,

    var auctionBidNickname : String?,
    var auctionBidPrice : String?,
    )
//이미지, 상태, 타이틀, index


data class AuctionProductInfo(
    var auctionProductIdx : Long?,
    var auctionProductName : String?,
    var auctionProductDetail : String?,
    var auctionProductStoreIdx : Long?,

    var auctionProductOpenPrice : String?,
    var auctionProductOpenDate : String?,
    var auctionProductCloseDate : String?,

    var auctionProductImageList : ArrayList<String>?
)

