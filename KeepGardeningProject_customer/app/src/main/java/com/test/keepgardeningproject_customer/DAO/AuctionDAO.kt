package com.test.keepgardeningproject_customer.DAO

class AuctionDAO {
}

data class AuctionInfo(
    var auctionIdx: Long,
    var auctionState: String,
    var auctionAuctionProductIndex: Long,
    var auctionCustomerSuccessIndex: Long,
    var auctionCustomerList :ArrayList<String>,
)


