package com.test.keepgardeningproject_customer.DAO

class AuctionDAO {
}

data class AuctionInfo(var auctionIdx:Long,

    var auctionAuctionProductIndex:String,

    var auctionState:String,

    var auctionCustomerList:String) {

    companion object{
        val AUCTION_TYPE = 0
        val BID_TYPE = 1
        val Auction_TYPE2 = 2
    }
}

//이미지, 상태, 타이틀, index



