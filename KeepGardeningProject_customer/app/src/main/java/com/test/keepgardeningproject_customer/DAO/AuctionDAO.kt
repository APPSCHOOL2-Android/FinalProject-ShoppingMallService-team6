package com.test.keepgardeningproject_customer.DAO

class AuctionDAO {
}

data class AuctionInfo(var auctionIdx:Long,

    var auctionAuctionProductIndex:Long,

    var auctionState:String,

    var auctionCustomerList:ArrayList<Long>) {

    companion object{
        val AUCTION_TYPE = 0
        val BID_TYPE = 1
        val Auction_TYPE2 = 2
    }
}

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


